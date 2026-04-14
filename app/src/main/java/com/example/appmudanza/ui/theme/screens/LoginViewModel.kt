package com.example.appmudanza.ui.theme.screens

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(isLoading = true, error = null)
            try {
                val db = DatabaseProvider.getDatabase(getApplication())
                val user = db.userDao().getUserByEmail(email)
                
                if (user != null && user.password == password) {
                    uiState.value = uiState.value.copy(isLoggedIn = true, isLoading = false)
                } else {
                    uiState.value = uiState.value.copy(error = "Email ou senha incorretos", isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Erro ao fazer login", e)
                uiState.value = uiState.value.copy(error = "Erro técnico: ${e.message}", isLoading = false)
            }
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)
