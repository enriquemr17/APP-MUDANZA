package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.viewmodel.IncidenciaViewModel
import com.example.appmudanza.viewmodel.MudanzaViewModel
import java.text.SimpleDateFormat
import java.util.*import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciasScreen(
    onBack: () -> Unit,
    mudanzaViewModel: MudanzaViewModel = viewModel(),
    incidenciaViewModel: IncidenciaViewModel = viewModel()
) {
    val mudanzas by mudanzaViewModel.mudanzas.collectAsState()
    val incidencias by incidenciaViewModel.incidencias.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Incidencias") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (mudanzas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No hay mudanzas registradas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(mudanzas) { mudanza ->
                    var showIncidencia by remember { mutableStateOf(false) }
                    var tipoSeleccionado by remember { mutableStateOf("") }
                    val tiposIncidencia = listOf("Queja", "Denuncia", "Otro")

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${mudanza.origen} → ${mudanza.destino}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Conductor: ${if (mudanza.withDriver) mudanza.driverName else "Sin conductor"}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Estado: ${mudanza.estado}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = { showIncidencia = !showIncidencia }) {
                                Text("Abrir incidencia")
                            }

                            if (showIncidencia) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Tipo de incidencia:", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))

                                tiposIncidencia.forEach { tipo ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = tipoSeleccionado == tipo,
                                            onClick = { tipoSeleccionado = tipo }
                                        )
                                        Text(tipo)
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(
                                    onClick = {
                                        if (tipoSeleccionado.isNotEmpty()) {
                                            incidenciaViewModel.addIncidencia(
                                                mudanzaId = mudanza.id,
                                                tipo = tipoSeleccionado,
                                                origen = mudanza.origen,
                                                destino = mudanza.destino,
                                                fecha = System.currentTimeMillis()
                                            )
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Incidencia registrada correctamente")
                                            }
                                            showIncidencia = false
                                            tipoSeleccionado = ""
                                        } else {
                                            scope.launch {
                                                snackbarHostState.showSnackbar("Selecciona un tipo de incidencia")
                                            }
                                        }
                                    }
                                ) {
                                    Text("Confirmar incidencia")
                                }
                            }

                            // Incidencias asociadas a esta mudanza
                            val incidenciasMudanza = incidencias.filter { it.mudanzaId == mudanza.id }
                            if (incidenciasMudanza.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider()
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Incidencias registradas:", style = MaterialTheme.typography.bodyMedium)
                                incidenciasMudanza.forEach { incidencia ->
                                    val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                        .format(Date(incidencia.fecha))
                                    Text("• ${incidencia.tipo} - $fecha", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}