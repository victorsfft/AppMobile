package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models


data class Mensaje(
    var tipo: String = "",
    val args: MutableList<String> = mutableListOf()
) {
    fun addArg(arg: String) {
        args.add(arg)
    }
}