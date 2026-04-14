package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.ui.viewmodel.MoveViewModel
import com.example.appmudanza.viewmodel.VehicleViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MudanzaScreen(
    onBack: () -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel(),
    moveViewModel: MoveViewModel = viewModel()
) {
    val vehicles by vehicleViewModel.vehicles.collectAsState()
    val moves by moveViewModel.moves.collectAsState()

    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var client by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var selectedVehicleId by remember { mutableStateOf<Int?>(null) }
    var filterCapacity by remember { mutableStateOf("") }

    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Crear Mudanza", "Filtrar Vehículos", "Mudanzas Registradas")

    Scaffold(topBar = { TopAppBar(title = { Text("Gestión de Mudanzas") }) }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selectedTab) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // SELEÇÃO DE VEÍCULO
                        Text("Selecciona Vehículo", style = MaterialTheme.typography.titleMedium)
                        val vehiclesWithDriver = vehicles.filter { it.withDriver }

                        LazyColumn(
                            modifier = Modifier.height(200.dp), // Altura fixa para veículos
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(vehiclesWithDriver) { vehicle ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedVehicleId = vehicle.id }
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Placa: ${vehicle.plate}")
                                        Text("Tipo: ${vehicle.type}")
                                        Text("Capacidad: ${vehicle.capacity}")
                                        Text("Conductor: ${vehicle.driver}")
                                        Text("Carnet: ${vehicle.licenseType}")
                                        if (selectedVehicleId == vehicle.id) Text("✔ Seleccionado")
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        // FORMULÁRIO
                        Text("Datos de la Mudanza", style = MaterialTheme.typography.titleMedium)
                        OutlinedTextField(value = origin, onValueChange = { origin = it }, label = { Text("Origen") })
                        OutlinedTextField(value = destination, onValueChange = { destination = it }, label = { Text("Destino") })
                        OutlinedTextField(value = client, onValueChange = { client = it }, label = { Text("Cliente") })
                        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Fecha") })

                        Button(
                            onClick = {
                                selectedVehicleId?.let { vehicleId ->
                                    moveViewModel.addMove(origin, destination, date, client, vehicleId)
                                    origin = ""; destination = ""; client = ""; date = ""
                                }
                            },
                            enabled = selectedVehicleId != null,
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Crear Mudanza") }

                        if (selectedVehicleId == null) {
                            Text("Seleccione un vehículo primero", color = MaterialTheme.colorScheme.error)
                        }

                        Spacer(Modifier.height(20.dp))

                        // LISTA DE MUDANZAS (ROLAMENTO VERTICAL)
                        Text("Mudanzas Registradas", style = MaterialTheme.typography.titleMedium)
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(moves) { move ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Cliente: ${move.client}")
                                        Text("Origen: ${move.origin}")
                                        Text("Destino: ${move.destination}")
                                        Text("Fecha: ${move.date}")
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(8.dp))
                        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
                    }
                }

                1 -> {
                    val filteredVehicles = vehicles
                        .filter { it.withDriver }
                        .filter { vehicle -> vehicle.capacity >= (filterCapacity.toIntOrNull() ?: 0) }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
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

                        items(filteredVehicles) { vehicle ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedVehicleId = vehicle.id }
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Placa: ${vehicle.plate}")
                                    Text("Tipo: ${vehicle.type}")
                                    Text("Capacidad: ${vehicle.capacity}")
                                    Text("Conductor: ${vehicle.driver}")
                                    if (selectedVehicleId == vehicle.id) {
                                        Text("✔ Seleccionado")
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text("Mudanzas Registradas", style = MaterialTheme.typography.titleMedium)
                        }

                        items(moves) { move ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Cliente: ${move.client}")
                                    Text("Origen: ${move.origin}")
                                    Text("Destino: ${move.destination}")
                                    Text("Fecha: ${move.date}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
