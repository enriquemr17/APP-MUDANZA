package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.R
import com.example.appmudanza.viewmodel.VehicleViewModel



@Composable
fun ConductorCard (
    title: String,
    description: String,
    imagesRes: Int,
    valoration: Float,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable{onClick()},
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {  // CREACION DE FILA (ROW) PARA IMPLEMENTAR IMAGENES Y DESCRIPCIONES (CONDUCTORES)
            Image (
                painter = painterResource(id = imagesRes),
                contentDescription = title,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.titleSmall)
                Text(text = "Valoracion: $valoration / 5")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlquilerScreen(
    onBack: () -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel(),
    onConductorClick: (Int) -> Unit,


) {
    val vehicles by vehicleViewModel.vehicles.collectAsState()
    var filterCapacity by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Con Condutor", "Sin Condutor")


    Scaffold(topBar = { TopAppBar(title = { Text("Alquiler de Vehículos")},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

        })}) { padding ->

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
    0 -> { // vehiculos con conductor

        val filteredVehicles = vehicles
            .filter { it.withDriver } // vehiculos con conductor
            .filter { vehicle -> vehicle.capacity >= (filterCapacity.toIntOrNull() ?: 0) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(5.dp),
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
            itemsIndexed(filteredVehicles) {index, vehicle ->
                ConductorCard (
                    title = vehicle.driver,
                    description = vehicle.type,
                    imagesRes = R.drawable.ic_launcher_foreground,
                    valoration = vehicle.valoration,
                    onClick = {onConductorClick(index)},

                    )
            }

        }
        Spacer(Modifier.height(20.dp))
    }

    1 -> { // vehiculos sin conductor

        val filteredVehicles = vehicles
                .filter { !it.withDriver } // vehiculos sin conductor
                .filter { vehicle -> vehicle.capacity >= (filterCapacity.toIntOrNull() ?: 0) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Filtrar Vehículos por Capacidad",
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    value = filterCapacity,
                    onValueChange = { filterCapacity = it },
                    label = { Text("Cantidad a transportar") },

                )
            }



            items(filteredVehicles) { vehicle ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Matricula: ${vehicle.plate}")
                        Text("Tipo: ${vehicle.type}")
                        Text("Capacidad: ${vehicle.capacity}")

                    }
                }
            }

        }
    }
            }
        }
    }
}

