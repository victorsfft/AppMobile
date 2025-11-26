package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.LoginResult
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.repositories.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, informationText = "") }

            if (!validateFields()) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        informationText = "Por favor, complete todos los campos"
                    )
                }
                return@launch
            }

            val result = loginRepository.login(
                _state.value.email,
                _state.value.password
            )

            _state.update {
                when (result) {
                    is LoginResult.Success -> it.copy(
                        isLoading = false,
                        loginSuccess = true
                    )
                    is LoginResult.Error -> it.copy(
                        isLoading = false,
                        informationText = result.message
                    )
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        return _state.value.email.isNotBlank() &&
                _state.value.password.isNotBlank()
    }
}

// ui/screens/auth/LoginState.kt
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val informationText: String = "",
    val loginSuccess: Boolean = false
)