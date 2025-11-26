package com.iesfernandoaguilar.solsonafuentes.appmobile.data.models

sealed class LoginResult {
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}