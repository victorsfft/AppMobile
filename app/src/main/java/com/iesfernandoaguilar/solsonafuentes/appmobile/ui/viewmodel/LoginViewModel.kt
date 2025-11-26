package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.repositories.LoginRepository
import com.iesfernandoaguilar.solsonafuentes.appmobile.network.SocketConnection
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.SecureUtils
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.LoginState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository()

    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.login(email, password)
                .onSuccess { usuario ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = true,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccess = false,
                            error = exception.message
                        )
                    }
                }
        }
    }
}

