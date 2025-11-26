package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.enum.EstadoTarea
import java.io.Serializable

data class Tarea(
    val idTarea: Long? = null,
    val titulo: String = "",
    val descripcion: String? = null,
    //val prioridad: Prioridad = Prioridad.MEDIA,
    val estado: EstadoTarea = EstadoTarea.PENDIENTE,
    val fechaCreacion: String? = null,
    val fechaVencimiento: String? = null,
    val usuarioAsignado: Usuario? = null,
    val grupo: Grupo? = null,
    //val subgrupo: Subgrupo? = null
) : Serializable
