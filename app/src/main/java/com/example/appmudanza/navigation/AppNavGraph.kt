package com.example.appmudanza.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmudanza.ui.theme.screens.AlquilerScreen
import com.example.appmudanza.ui.theme.screens.HomeScreen
import com.example.appmudanza.ui.theme.screens.LoginScreen
import com.example.appmudanza.ui.theme.screens.MudanzaScreen
import com.example.appmudanza.ui.theme.screens.RegisterScreen
import com.example.appmudanza.ui.theme.screens.VehicleRegistrationScreen

// SEALED CLASS: rutas tipadas
sealed class Route(val path: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object Mudanza : Route("mudanza")
    object VehicleRegistration : Route("vehicle_registration")
    object Alquiler : Route("alquiler")
    object Incidencias : Route("incidencias")
    object Settings : Route("settings")
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Route.Login.path
    ) {

        composable(Route.Login.path) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Route.Register.path)
                }
            )
        }

        composable(Route.Register.path) {
            RegisterScreen(
                onRegister = {
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Home.path) {
            HomeScreen(
                navController = navController,
                onGoToMudanza = {
                    navController.navigate(Route.Mudanza.path)
                },
                onGoToVehicleRegistration = {
                    navController.navigate(Route.VehicleRegistration.path)
                },
                onGoToAlquiler = {
                    navController.navigate(Route.Alquiler.path)
                },
                onGoToIncidencias = {
                    navController.navigate(Route.Incidencias.path)
                },
                onGoToSettings = {
                    navController.navigate(Route.Settings.path)
                }
            )
        }

        composable(Route.VehicleRegistration.path) {
            VehicleRegistrationScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Mudanza.path) {
            MudanzaScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Alquiler.path) {
            AlquilerScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Incidencias.path) {
            Text("INCIDENCIAS SCREEN")
        }

        composable(Route.Settings.path) {
            Text("AJUSTES SCREEN")
        }
    }
}