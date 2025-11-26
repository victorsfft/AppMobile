package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.solsonafuentes.appmobile.data.models.Usuario
import com.iesfernandoaguilar.solsonafuentes.appmobile.network.SocketConnection
import com.iesfernandoaguilar.solsonafuentes.appmobile.util.Session
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GrupoViewModel : ViewModel() {

    private val _empleados = MutableStateFlow<List<Usuario>>(emptyList())
    val empleados = _empleados.asStateFlow()

    private val _subgrupos = MutableStateFlow<List<Subgrupo>>(emptyList())
    val subgrupos = _subgrupos.asStateFlow()

    private val _selectedMenu = MutableStateFlow("gestion")
    val selectedMenu = _selectedMenu.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            // Cargar empleados - igual que GrupoController
            val mensaje = hashMapOf(
                "tipo" to "OBTENER_EMPLEADOS",
                "idGrupo" to Session.grupoActual?.idGrupo
            )

            SocketConnection.send(mensaje)
            val response = SocketConnection.receive() as? HashMap<*, *>

            if (response?.get("tipo") == "LISTA_EMPLEADOS") {
                val listaEmpleados = response["empleados"] as? List<Usuario>
                _empleados.value = listaEmpleados ?: emptyList()
            }
        }
    }

    fun cambiarSubgrupo(idSubgrupo: Long) {
        viewModelScope.launch {
            val mensaje = hashMapOf(
                "tipo" to "CAMBIAR_SUBGRUPO",
                "idSubgrupo" to idSubgrupo
            )

            SocketConnection.send(mensaje)
            val response = SocketConnection.receive() as? HashMap<*, *>

            if (response?.get("tipo") == "SUBGRUPO_CAMBIADO") {
                cargarDatos()
            }
        }
    }

    fun anadirEmpleado() {
        // Igual que en GrupoController.anadirEmpleadoDialog()
        viewModelScope.launch {
            // Lógica para añadir empleado
        }
    }
}