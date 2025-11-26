package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

data class LoginRequest(
    val tipo: String,
    val email: String,
    val password: String
)