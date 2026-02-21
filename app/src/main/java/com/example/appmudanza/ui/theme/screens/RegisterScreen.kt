package com.example.appmudanza.ui.theme.screens

import android.graphics.Outline
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text

@Composable

fun RegisterScreen (
    onRegister: () -> Unit,
    onBackToLogin: () -> Unit,
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable {mutableStateOf("")}
    var repitePassword by rememberSaveable {mutableStateOf("")}
    val isvalid = email.isNotBlank() && password.length >= 4
    val passwordOk = password.isNotEmpty() && password == repitePassword

    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("REGISTRO", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text ("Email")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {Text("Contraseña")},
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = repitePassword,
                onValueChange = {repitePassword = it},
                label = {Text("Repite la contraseña")},
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onRegister,
                enabled = passwordOk
            ) {
                Text("Registrarse")
            }
            TextButton(
                onClick = onBackToLogin
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }


}