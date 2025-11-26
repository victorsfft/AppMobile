package com.iesfernandoaguilar.solsonafuentes.appmobile.util

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Grupo
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object Session {
    var usuario: Any? = null
    var grupoActual: Grupo? = null
    var inputStream: ObjectInputStream? = null
    var outputStream: ObjectOutputStream? = null
    var hiloUsuarioCreado = false

    fun clear() {
        usuario = null
        grupoActual = null
        inputStream = null
        outputStream = null
        hiloUsuarioCreado = false
    }
}