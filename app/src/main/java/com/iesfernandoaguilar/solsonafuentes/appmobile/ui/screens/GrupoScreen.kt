package com.iesfernandoaguilar.solsonafuentes.appmobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrupoScreen() {
    var selectedMenu by remember { mutableStateOf("gestion") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Menú lateral como en JavaFX
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Inicio") },
                    selected = selectedMenu == "inicio",
                    onClick = { selectedMenu = "inicio" }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Task, null) },
                    label = { Text("Tareas") },
                    selected = selectedMenu == "tareas",
                    onClick = { selectedMenu = "tareas" }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.CalendarMonth, null) },
                    label = { Text("Calendario") },
                    selected = selectedMenu == "calendario",
                    onClick = { selectedMenu = "calendario" }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Group, null) },
                    label = { Text("Gestión") },
                    selected = selectedMenu == "gestion",
                    onClick = { selectedMenu = "gestion" }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Grupo") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            when (selectedMenu) {
                "gestion" -> GestionContent(Modifier.padding(padding))
                "tareas" -> TareasContent(Modifier.padding(padding))
                else -> InicioContent(Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun GestionContent(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Card Añadir
        item {
            Card(
                onClick = { /* Añadir empleado */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Text("Añadir Empleado")
                }
            }
        }

        // Cards de empleados (ejemplo)
        items(5) { index ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Empleado ${index + 1}", style = MaterialTheme.typography.titleMedium)
                        Text("empleado${index + 1}@empresa.com")
                    }
                }
            }
        }
    }
}

@Composable
fun TareasContent(modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Tareas - Por implementar")
    }
}

@Composable
fun InicioContent(modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Inicio - Por implementar")
    }
}