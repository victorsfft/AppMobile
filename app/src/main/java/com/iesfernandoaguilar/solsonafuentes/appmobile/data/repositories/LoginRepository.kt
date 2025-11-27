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
            val saltMsg = Mensaje(tipo = "OBTENER_SALT")
            saltMsg.addArg(email)
            SocketConnection.sendMessage(saltMsg)

            val saltResponse = SocketConnection.receiveMessage()

            if (saltResponse?.tipo != "ENVIAR_SALT") {
                return@withContext Result.failure(Exception("Error obteniendo salt"))
            }

            val respuesta = saltResponse.args.getOrNull(0)
            val saltString = saltResponse.args.getOrNull(1)


            val saltBytes = Base64.decode(saltString, Base64.NO_WRAP)
            val hashedPassword = SecureUtils.generarHashSHA512(password, saltBytes)

            val loginMsg = Mensaje(tipo = "INICIAR_SESION")
            loginMsg.addArg(email)
            loginMsg.addArg(hashedPassword)
            SocketConnection.sendMessage(loginMsg)

            // Manejar respuesta
            when (respuesta) {
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

    /*
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
    */
}