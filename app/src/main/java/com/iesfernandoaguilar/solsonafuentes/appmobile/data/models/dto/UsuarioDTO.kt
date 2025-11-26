package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.dto

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Usuario
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.enum.Rol
import java.io.Serializable

data class UsuarioDTO(
    val idUsuario: Long? = null,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val rol: String  // String en vez de enum para JSON
) : Serializable {
    // Conversiones
    fun toUsuario(): Usuario = Usuario(
        idUsuario = idUsuario,
        nombre = nombre,
        apellidos = apellidos,
        email = email,
        rol = Rol.valueOf(rol)
    )

    companion object {
        fun fromUsuario(usuario: Usuario): UsuarioDTO = UsuarioDTO(
            idUsuario = usuario.idUsuario,
            nombre = usuario.nombre,
            apellidos = usuario.apellidos,
            email = usuario.email,
            rol = usuario.rol.name
        )
    }
}