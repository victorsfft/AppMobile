package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.enum.Rol
import java.io.Serializable

data class Usuario(
    val idUsuario: Long? = null,
    val nombre: String = "",
    val apellidos: String = "",
    val email: String = "",
    val telefono: String? = null,
    val direccion: String? = null,
    val password: String? = null,
    val rol: Rol = Rol.EMPLEADO,
    val activo: Boolean = true,
    val fechaRegistro: String? = null,
    val fechaUltimaConexion: String? = null,
    val tokenRecuperacion: String? = null,
    val tokenExpiracion: String? = null
) : Serializable