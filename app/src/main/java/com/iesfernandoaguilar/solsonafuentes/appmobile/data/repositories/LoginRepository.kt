package com.iesfernandoaguilar.solsonafuentes.appmobile.data.repositories

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.LoginRequest
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.LoginResult
import com.iesfernandoaguilar.solsonafuentes.appmobile.network.SocketConnection

class LoginRepository(private val socketConnection: SocketConnection) {
    suspend fun login(email: String, password: String): LoginResult {
        return try {
            if (!socketConnection.connect()) {
                return LoginResult.Error("No se pudo conectar al servidor")
            }

            val loginRequest = LoginRequest(
                tipo = "LOGIN",
                email = email,
                password = password
            )

            socketConnection.send(loginRequest)
            val response = socketConnection.receive() as? HashMap<*, *>

            if (response?.get("success") == true) {
                LoginResult.Success
            } else {
                LoginResult.Error(response?.get("mensaje") as? String ?: "Error en el inicio de sesión")
            }
        } catch (e: Exception) {
            LoginResult.Error("Error de conexión: ${e.message}")
        }
    }
}