package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appmudanza.viewmodel.MudanzaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MudanzaCard(
    mudanza: com.example.appmudanza.data.entity.Mudanza,
    mudanzaViewModel: MudanzaViewModel,
    snackbarHostState: SnackbarHostState
) {
    var showGestionar by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope ()
    val fechaFormateada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(Date(mudanza.fecha))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Origen: ${mudanza.origen}", style = MaterialTheme.typography.titleMedium)
                Text("Destino: ${mudanza.destino}", style = MaterialTheme.typography.titleMedium)
                Text("Fecha: $fechaFormateada")
                Text("Conductor: ${if (mudanza.withDriver) mudanza.driverName else "Sin conductor"}")
                Text("Estado: ${mudanza.estado}")

                Spacer (modifier = Modifier.height(8.dp))

                Button (onClick = {showGestionar = !showGestionar}) {
                    Text("Gestionar reserva")
                }

                if (showGestionar) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            mudanzaViewModel.cancelarMudanza(mudanza.id)
                            scope.launch { snackbarHostState.showSnackbar("Reserva cancelada") }
                                showGestionar = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cancelar reserva")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button (onClick = {showDatePicker = true}) {
                        Text("Cambiar fecha")
                    }

                    if (showDatePicker) {
                        val datePickerState = rememberDatePickerState()
                        DatePickerDialog (
                            onDismissRequest = {showDatePicker = false},
                            confirmButton = {
                                Button(onClick =  {
                                    val nuevaFecha = datePickerState.selectedDateMillis ?: mudanza.fecha
                                    mudanzaViewModel.cambiarFecha(mudanza.id, nuevaFecha)
                                    scope.launch {snackbarHostState.showSnackbar("Fecha actualizada correctamente")}
                                    showDatePicker = false
                                    showGestionar = false
                                }) {
                                    Text("Confirmar")
                                }
                            },
                            dismissButton = {
                                Button(onClick = {showDatePicker = false}) {
                                    Text("Cancerlar")
                                }
                            }
                        ) {
                            DatePicker (state = datePickerState)
                        }
                    }
                    if (mudanza.estado == "cancelada") {
                        Spacer(modifier = Modifier.height(8.dp))
                        IconButton(onClick = {
                            mudanzaViewModel.deleteMudanza(mudanza.id)
                            scope.launch {
                                snackbarHostState.showSnackbar("Mudanza eliminada")
                            }
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar mudanza",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

}