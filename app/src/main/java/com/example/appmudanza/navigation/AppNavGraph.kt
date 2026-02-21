package com.example.appmudanza.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmudanza.ui.theme.screens.HomeScreen
import com.example.appmudanza.ui.theme.screens.LoginScreen
import com.example.appmudanza.ui.theme.screens.RegisterScreen

//SEALED CLASS: define las rutas de la APP de forma tipada

sealed class Route (val Path: String) {

    //cada objeto representa una pantalla con su "path" o ruta única.

        data object Login : Route ("login")
        data object Register : Route ("register")
        data object Home : Route ("home")
        data object Mudanza : Route ("conductor para mudanza")
        data object Alquiler : Route ("alquiler sin conductor")
        data object Incidencias : Route ("indidencias")
        data object Settings  : Route ("ajustes")

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
        onGoToRegister = {
            //Navegacion a pantalla REGISTER
            navController.navigate(Route.Register.Path)
        }
    )
}
    composable (Route.Register.Path){
        RegisterScreen (
            onRegister = {
                navController.navigate(Route.Home.Path){
                    popUpTo(Route.Login.Path) {inclusive = true} //evita que pete al ir hacia atrás
                }
            },

            onBackToLogin = {
                navController.navigate(Route.Login.Path){
                    navController.popBackStack()
                }
            }
        )
    }
    composable (Route.Home.Path){
            HomeScreen (
                onGoToMudanza = {
                navController.navigate(Route.Mudanza.Path){}
                },
            onGoToAlquiler = {
                navController.navigate(Route.Alquiler.Path){}
            },

            onGoToIncidencias = {
                navController.navigate(Route.Incidencias.Path){}
            },

            onGoToSettings = {
                navController.navigate(Route.Settings.Path){}
            }

        )
    }
}

}