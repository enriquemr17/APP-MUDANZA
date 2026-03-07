package com.example.appmudanza.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmudanza.ui.theme.screens.HomeScreen
import com.example.appmudanza.ui.theme.screens.LoginScreen
import com.example.appmudanza.ui.theme.screens.MudanzaScreen
import com.example.appmudanza.ui.theme.screens.RegisterScreen



// SEALED CLASS: rutas tipadas
sealed class Route(val Path: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object Mudanza : Route("mudanza")
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
        startDestination = Route.Login.Path
    ) {
        composable(Route.Login.Path) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.Path) {
                        popUpTo(Route.Login.Path) { inclusive = true }
                    }
                },
                onGoToRegister = {
                    navController.navigate(Route.Register.Path)
                }
            )
        }

        composable(Route.Register.Path) {
            RegisterScreen(
                onRegister = {
                    navController.navigate(Route.Home.Path) {
                        popUpTo(Route.Login.Path) { inclusive = true }
                    }
                },
                onBackToLogin = {
                    navController.popBackStack()  // Simples volta
                }
            )
        }

        composable(Route.Home.Path) {
            HomeScreen(
                onGoToMudanza = {
                    navController.navigate(Route.Mudanza.Path)
                },
                onGoToAlquiler = {
                    navController.navigate(Route.Alquiler.Path)
                },
                onGoToIncidencias = {
                    navController.navigate(Route.Incidencias.Path)
                },
                onGoToSettings = {
                    navController.navigate(Route.Settings.Path)
                }
            )
        }

        // Rotas vazias por enquanto (adicionamos depois)
        composable(Route.Mudanza.Path) {
            MudanzaScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Route.Alquiler.Path) {
            Text("ALQUILER SCREEN - EM DESENVOLVIMENTO")
        }
        composable(Route.Incidencias.Path) {
            Text("INCIDENCIAS SCREEN")
        }
        composable(Route.Settings.Path) {
            Text("AJUSTES SCREEN")
        }
    }
}
