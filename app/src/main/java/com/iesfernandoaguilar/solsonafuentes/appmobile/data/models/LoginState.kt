package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

data class LoginState(
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)