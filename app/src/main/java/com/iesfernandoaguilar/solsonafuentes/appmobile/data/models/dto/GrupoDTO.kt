package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.dto

import java.io.Serializable

data class GrupoDTO(
    val idGrupo: Long?,
    val nombre: String,
    val codigo: String,
    val vat: String?
) : Serializable