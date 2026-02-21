package com.example.appmudanza.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

//SEALED CLASS: define las rutas de la APP de forma tipada

sealed class Route (val Path: String) {

    //cada objeto representa una pantalla con su "path" o ruta única.

        data object Login : Route ("login")
        data object Register : Route ("register")
        data object Home : Route ("home")
}

@Composable
fun AppNavGraph (
    navController: NavHostController = rememberNavController ()
) {
NavHost (
    navController = navController,
    startDestination = Route.Login.Path // hacemos que LOGIN sea lo primero que veamos
) {
composable (Route.Login.Path) {
    //ahora mostramos los botones que queremos que aparezcan y sus acciones
    LoginScreen (
        onLogin = {
            //simulacion de login OK
            // Navegacion a pantalla HOME
            navController.navigate(Route.Home.Path) {
                popUpTo(Route.Login.Path) { inclusive = true} //evitamos que la pagina pete al ir hacia atras.
            }
        },
        onGoToRegisyer
    )
}
}
}