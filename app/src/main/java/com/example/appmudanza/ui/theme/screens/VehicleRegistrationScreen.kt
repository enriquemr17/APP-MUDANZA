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
import com.example.appmudanza.viewmodel.VehicleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleRegistrationScreen(
    onBack: () -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel()
) {
    val vehicles by vehicleViewModel.vehicles.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Con Condutor", "Sin Condutor")

    // Campos aba 1 (con condutor)
    var plate by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var driver by remember { mutableStateOf("") }
    var licenseType by remember { mutableStateOf("") }

    // Campos aba 2 (sin condutor)
    var plateNoDriver by remember { mutableStateOf("") }
    var typeNoDriver by remember { mutableStateOf("") }
    var capacityNoDriver by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Vehículos") }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            when (selectedTab) {

                0 -> { // Veículos com condutor
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text("Nuevo Vehículo con Conductor", style = MaterialTheme.typography.titleMedium)

                            OutlinedTextField(value = plate, onValueChange = { plate = it }, label = { Text("Placa") })
                            OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Tipo") })
                            OutlinedTextField(value = capacity, onValueChange = { capacity = it }, label = { Text("Capacidad") })
                            OutlinedTextField(value = driver, onValueChange = { driver = it }, label = { Text("Conductor") })
                            OutlinedTextField(value = licenseType, onValueChange = { licenseType = it }, label = { Text("Tipo de Carnet") })

                            Spacer(Modifier.height(12.dp))

                            Button(onClick = {
                                vehicleViewModel.addVehicle(
                                    plate,
                                    type,
                                    capacity.toIntOrNull() ?: 0,
                                    driver,
                                    licenseType = licenseType,
                                    withDriver = true
                                )
                                plate = ""; type = ""; capacity = ""; driver = ""; licenseType = ""
                            }) {
                                Text("Guardar Vehículo con Conductor")
                            }

                            Spacer(Modifier.height(20.dp))
                            Text("Vehículos con Conductor Registrados", style = MaterialTheme.typography.titleMedium)
                        }

                        items(vehicles.filter { it.withDriver }) { vehicle ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Placa: ${vehicle.plate}")
                                    Text("Tipo: ${vehicle.type}")
                                    Text("Capacidad: ${vehicle.capacity}")
                                    Text("Conductor: ${vehicle.driver}")
                                    Text("Carnet: ${vehicle.licenseType}")
                                }
                            }
                        }

                        item { Spacer(Modifier.height(20.dp)) }
                        item { Button(onClick = onBack) { Text("Volver") } }
                    }
                }

                1 -> { // Veículos sem condutor
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text("Nuevo Vehículo sin Conductor", style = MaterialTheme.typography.titleMedium)

                            OutlinedTextField(value = plateNoDriver, onValueChange = { plateNoDriver = it }, label = { Text("Placa") })
                            OutlinedTextField(value = typeNoDriver, onValueChange = { typeNoDriver = it }, label = { Text("Tipo") })
                            OutlinedTextField(value = capacityNoDriver, onValueChange = { capacityNoDriver = it }, label = { Text("Capacidad") })

                            Spacer(Modifier.height(12.dp))

                            Button(onClick = {
                                vehicleViewModel.addVehicle(
                                    plateNoDriver,
                                    typeNoDriver,
                                    capacityNoDriver.toIntOrNull() ?: 0,
                                    driver = "",
                                    licenseType = "",
                                    withDriver = false
                                )
                                plateNoDriver = ""; typeNoDriver = ""; capacityNoDriver = ""
                            }) {
                                Text("Guardar Vehículo sin Conductor")
                            }

                            Spacer(Modifier.height(20.dp))
                            Text("Vehículos sin Conductor Registrados", style = MaterialTheme.typography.titleMedium)
                        }

                        items(vehicles.filter { !it.withDriver }) { vehicle ->
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text("Placa: ${vehicle.plate}")
                                    Text("Tipo: ${vehicle.type}")
                                    Text("Capacidad: ${vehicle.capacity}")
                                }
                            }
                        }

                        item { Spacer(Modifier.height(20.dp)) }
                        item { Button(onClick = onBack) { Text("Volver") } }
                    }
                }
            }
        }
    }
}