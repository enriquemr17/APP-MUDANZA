package com.example.appmudanza.ui.theme.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.R
import com.example.appmudanza.data.entity.Vehicle
import kotlinx.coroutines.launch
import com.example.appmudanza.viewmodel.MudanzaViewModel


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ConductorDetalladoScreen(
    vehicle : Vehicle,
    onBack: () -> Unit,
    mudanzaViewModel: MudanzaViewModel = viewModel()

) {
    var showDatePicker by remember { mutableStateOf(false) }
    var origen by remember { mutableStateOf("") }
    var destino by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope ()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Conductor") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = vehicle.type,
                modifier = Modifier.fillMaxWidth().size(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = vehicle.driver,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =vehicle.type,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text (
                text = vehicle.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = origen,
                onValueChange = {origen = it},
                label = {Text("Origen")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer (modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = destino,
                onValueChange = {destino = it},
                label = {Text("Destino")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer (modifier = Modifier.height(8.dp))

            Button(onClick = {showDatePicker = true}) {
                Text("Reservar cita")
            }

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = {showDatePicker = false }, // cierra el calendario
                    confirmButton = {
                        Button(onClick = {val  selectedDate = datePickerState.selectedDateMillis ?: 0L
                            mudanzaViewModel.addMudanza (
                                origen = origen,
                                destino = destino,
                                fecha = selectedDate,
                                withDriver = vehicle.withDriver,
                                driverName = vehicle.driver
                            )
                            scope.launch { snackbarHostState.showSnackbar("Reserva confirmada") }
                            showDatePicker= false
                            origen = ""
                            destino = ""
                        }) {
                            Text("Confirmar")
                        }

                    },
                    dismissButton = {
                        Button(onClick = {showDatePicker = false}) {
                            Text("Cancelar")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}
