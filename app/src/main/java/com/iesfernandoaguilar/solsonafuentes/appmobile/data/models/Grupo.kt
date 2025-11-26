package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

import java.io.Serializable

data class Grupo(
    val idGrupo: Long? = null,
    val nombre: String = "",
    val descripcion: String? = null,
    val codigo: String = "",
    val usuarioPropietario: Usuario? = null,
    val vat: String? = null,
    val fechaCreacion: String? = null,
   // val configuracion: ConfiguracionGrupo? = null
) : Serializable
