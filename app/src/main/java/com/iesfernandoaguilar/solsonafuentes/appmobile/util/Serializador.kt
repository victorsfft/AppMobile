package com.iesfernandoaguilar.solsonafuentes.appmobile.util

import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Mensaje

object Serializador {
    fun codificarMensaje(mensaje: Mensaje): String {
        return mensaje.tipo + ";" + mensaje.args.joinToString(";")
    }

    fun decodificarMensaje(linea: String): Mensaje {
        val partes = linea.split(";")
        val mensaje = Mensaje()
        mensaje.tipo = partes[0]
        if (partes.size > 1) {
            for (i in 1 until partes.size) {
                mensaje.addArg(partes[i])
            }
        }
        return mensaje
    }
}