package com.iesfernandoaguilar.solsonafuentes.appmobile.data.repositories

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Mensaje
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Usuario
import com.iesfernandoaguilar.solsonafuentes.appmobile.network.SocketConnection
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.SecureUtils
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {

    suspend fun login(email: String, password: String): Result<Usuario> = withContext(Dispatchers.IO) {
        try {
            // Conectar si no está conectado
            if (!SocketConnection.isConnected()) {
                val connected = SocketConnection.connect()
                if (!connected) {
                    return@withContext Result.failure(Exception("No se pudo conectar al servidor"))
                }
            }

            // Enviar credenciales
            val mensaje = Mensaje(
                tipo = "INICIAR_SESION",
                contenido = hashMapOf(
                    "email" to email,
                    "password" to SecureUtils.hashPassword(password)
                )
            )

            SocketConnection.send(mensaje)

            // Recibir respuesta
            val response = SocketConnection.receive() as? Mensaje

            return@withContext when (response?.tipo) {
                "INICIAR_SESION_EXITOSO" -> {
                    val usuarioMap = response.contenido["usuario"] as? HashMap<*, *>
                    if (usuarioMap != null) {
                        val usuario = Usuario(
                            idUsuario = (usuarioMap["idUsuario"] as? Number)?.toLong(),
                            nombre = usuarioMap["nombre"] as? String ?: "",
                            apellidos = usuarioMap["apellidos"] as? String ?: "",
                            email = usuarioMap["email"] as? String ?: ""
                        )

                        // Guardar en sesión
                        Session.usuario = usuario
                        Session.inputStream = SocketConnection.input
                        Session.outputStream = SocketConnection.output

                        Result.success(usuario)
                    } else {
                        Result.failure(Exception("Datos de usuario inválidos"))
                    }
                }
                "ERROR" -> {
                    val mensaje = response.contenido["mensaje"] as? String ?: "Error desconocido"
                    Result.failure(Exception(mensaje))
                }
                else -> {
                    Result.failure(Exception("Respuesta inesperada del servidor"))
                }
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
                    "password" to SecureUtils.hashPassword(password)
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