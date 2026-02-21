package com.example.appmudanza.ui.theme.screens

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable

fun HomeScreen (
    onGoToMudanza: () -> Unit,
    onGoToAlquiler: () -> Unit,
    onGoToIncidencias: () -> Unit,
    onGoToSettings: () -> Unit,
){
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "INICIO")
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onGoToMudanza,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ALQUILER DE CONDCUTOR + VEHICULO DE MUDANZA")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGoToAlquiler,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ALQUILA TU VEHICULO SIN CONDUCTOR")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGoToIncidencias,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("TUS INCIDENCIAS")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onGoToSettings,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("AJUSTES")
            }
        }
    }
}