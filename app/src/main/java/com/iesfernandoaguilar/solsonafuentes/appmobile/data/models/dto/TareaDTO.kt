package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.dto

import java.io.Serializable

data class TareaDTO(
    val idTarea: Long?,
    val titulo: String,
    val descripcion: String?,
    val prioridad: String,
    val estado: String,
    val fechaVencimiento: String?,
    val idUsuarioAsignado: Long?
) : Serializable