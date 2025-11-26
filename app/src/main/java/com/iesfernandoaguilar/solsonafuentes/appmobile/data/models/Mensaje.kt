package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

import java.io.Serializable

data class Mensaje(
    val tipo: String,
    val contenido: HashMap<String, Any> = hashMapOf(),
    val timestamp: Long = System.currentTimeMillis()
) : Serializable
