package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appmudanza.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onGoToMudanza: () -> Unit,
    onGoToVehicleRegistration: () -> Unit,
    onGoToAlquiler: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Bienvenido a AppMudanza",
                style = MaterialTheme.typography.titleLarge
            )

            // Botão para registrar veículos
            Button(
                onClick = onGoToVehicleRegistration,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Vehículo")
            }

            // Botão para gestão de mudanzas
            Button(
                onClick = onGoToMudanza,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gestión de Mudanza")
            }

            // Botões adicionais (Alquiler, Incidencias, Settings)
            Button(
                onClick = onGoToAlquiler,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Alquiler")
            }

            Button(
                onClick = onGoToIncidencias,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Incidencias")
            }

            Button(
                onClick = onGoToSettings,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ajustes")
            }
        }
    }
}