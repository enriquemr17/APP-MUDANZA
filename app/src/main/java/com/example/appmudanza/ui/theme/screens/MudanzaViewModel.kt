package com.example.appmudanza.ui.theme.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.MudanzaVehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MudanzaViewModel(application: Application) : AndroidViewModel(application) {
    private val _vehicles = MutableStateFlow<List<MudanzaVehicle>>(emptyList())
    val vehicles: StateFlow<List<MudanzaVehicle>> = _vehicles.asStateFlow()

    fun loadMudanzas() {  // Solo vehículos con conductor
        viewModelScope.launch {
            val db = DatabaseProvider.getDatabase(getApplication())
            val list = db.mudanzaVehicleDao().getAll()
            _vehicles.value = list
        }
    }
}
