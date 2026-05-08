package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmudanza.viewmodel.MudanzaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MudanzaScreen(
    onBack: () -> Unit,
    mudanzaViewModel: MudanzaViewModel = viewModel()
) {
    val mudanzas by mudanzaViewModel.mudanzas.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Mudanzas") },
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
                Text("No hay mudanzas reservadas")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(mudanzas) { mudanza ->
                    MudanzaCard(
                        mudanza = mudanza,
                        mudanzaViewModel = mudanzaViewModel,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    }
}