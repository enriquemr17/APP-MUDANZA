package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.viewmodel.VehicleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlquilerScreen(
    onBack: () -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel(),

) {
    val vehicles by vehicleViewModel.vehicles.collectAsState()
    var filterCapacity by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Alquiler de Vehículos")},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

        })}) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Filtrar Vehículos por Capacidad", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = filterCapacity,
                    onValueChange = { filterCapacity = it },
                    label = { Text("Cantidad a transportar") }
                )
            }

            val filteredVehicles = vehicles
                .filter { !it.withDriver } // vehiculos sin conductor
                .filter { vehicle -> vehicle.capacity >= (filterCapacity.toIntOrNull() ?: 0) }

            items(filteredVehicles) { vehicle ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Matricula: ${vehicle.plate}")
                        Text("Tipo: ${vehicle.type}")
                        Text("Capacidad: ${vehicle.capacity}")
                    }
                }
            }

            item { Spacer(Modifier.height(20.dp)) }

        }
    }
}