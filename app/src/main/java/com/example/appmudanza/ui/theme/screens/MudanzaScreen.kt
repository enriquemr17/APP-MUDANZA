package com.example.appmudanza.ui.theme.screens


import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MudanzaScreen (
    onBack: () -> Unit,
    onConductorClick: (Int) -> Unit,
    vehicleViewModel: VehicleViewModel = viewModel()
){

    val vehicles by vehicleViewModel.vehicles.collectAsState()
    val conductores = vehicles.filter {it.withDriver}

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("ALQUILER DE CONDUCTOR")},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {paddingValues->
        LazyColumn(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            itemsIndexed(conductores) {index, vehicle ->
                ConductorCard (
                    title = vehicle.driver,
                    description = vehicle.type,
                    imagesRes = R.drawable.ic_launcher_foreground,
                    valoration = vehicle.valoration,
                    onClick = {onConductorClick(index)},

                )
            }
        }
    }
}
