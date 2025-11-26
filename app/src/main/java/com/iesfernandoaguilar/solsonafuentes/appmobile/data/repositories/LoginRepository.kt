package com.iesfernandoaguilar.solsonafuentes.appmobile.data.repositories

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Mensaje
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Usuario
import com.iesfernandoaguilar.solsonafuentes.appmobile.network.SocketConnection
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.SecureUtils
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.io.encoding.ExperimentalEncodingApi
import android.util.Base64  // NO java.util.Base64Reintentar

class LoginRepository {
    private val TAG = "LoginRepository"

    @OptIn(ExperimentalEncodingApi::class)
    suspend fun login(email: String, password: String): Result<Usuario> = withContext(Dispatchers.IO) {
        try {
            if (!SocketConnection.isConnected()) {
                SocketConnection.connect()
            }

            // PASO 1: Pedir el salt
            val saltMsg = Mensaje(
                tipo = "OBTENER_SALT",
                contenido = hashMapOf()
            )
            saltMsg.contenido["arg0"] = email  // El servidor espera args

            SocketConnection.send(saltMsg)
            val saltResponse = SocketConnection.receive() as? Mensaje

            if (saltResponse?.tipo != "ENVIAR_SALT") {
                return@withContext Result.failure(Exception("Error obteniendo salt"))
            }

            val respuesta = saltResponse.contenido["arg0"] as? String
            if (respuesta == "no_salt") {
                return@withContext Result.failure(Exception("Usuario no existe"))
            }

            // PASO 2: Hashear contraseña con el salt recibido
            val saltString = saltResponse.contenido["arg1"] as? String ?: ""
            val saltBytes = Base64.decode(saltString, Base64.NO_WRAP)
            val hashedPassword = SecureUtils.generarHashSHA512(password, saltBytes)

            // PASO 3: Enviar login con contraseña hasheada
            val loginMsg = Mensaje(
                tipo = "INICIAR_SESION",
                contenido = hashMapOf()
            )
            loginMsg.contenido["arg0"] = email
            loginMsg.contenido["arg1"] = hashedPassword

            SocketConnection.send(loginMsg)
            val loginResponse = SocketConnection.receive() as? Mensaje

            // Manejar respuesta
            when (loginResponse?.tipo) {
                "SESION_ACTIVA" -> {
                    // Login exitoso
                    Result.success(Usuario()) // Parsear usuario de la respuesta
                }
                else -> Result.failure(Exception("Login fallido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun register(
        nombre: String,
        apellidos: String,
        email: String,
        password: String
    ): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            if (!SocketConnection.isConnected()) {
                SocketConnection.connect()
            }

            val mensaje = Mensaje(
                tipo = "REGISTRO",
                contenido = hashMapOf(
                    "nombre" to nombre,
                    "apellidos" to apellidos,
                    "email" to email,
                    "password" to SecureUtils.generarHashSHA512(password, SecureUtils.getSalt())
                )
            )

            SocketConnection.send(mensaje)

            val response = SocketConnection.receive() as? Mensaje

            when (response?.tipo) {
                "REGISTRO_EXITOSO" -> Result.success(true)
                "ERROR" -> {
                    val error = response.contenido["mensaje"] as? String ?: "Error de registro"
                    Result.failure(Exception(error))
                }
                else -> Result.failure(Exception("Error desconocido"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}