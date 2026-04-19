package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Mudanza(val titulo: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciasScreen(
    onBack: () -> Unit,
    onMudanzaClick: (Mudanza) -> Unit
) {
    val mudanzas = listOf(
        Mudanza("Mudanza Madrid - Alcalá"),
        Mudanza("Mudanza Sevilla - Málaga"),
        Mudanza("Mudanza Barcelona - Valencia")
    )

    Scaffold(
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(mudanzas) { mudanza ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onMudanzaClick(mudanza) }
                ) {
                    Text(
                        text = mudanza.titulo,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}