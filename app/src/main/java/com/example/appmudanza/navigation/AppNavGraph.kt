package com.example.appmudanza.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appmudanza.ui.theme.screens.AlquilerScreen
import com.example.appmudanza.ui.theme.screens.DetalleIncidenciaScreen
import com.example.appmudanza.ui.theme.screens.HomeScreen
import com.example.appmudanza.ui.theme.screens.IncidenciasScreen
import com.example.appmudanza.ui.theme.screens.LoginScreen
import com.example.appmudanza.ui.theme.screens.MudanzaScreen
import com.example.appmudanza.ui.theme.screens.RegisterScreen
import com.example.appmudanza.ui.theme.screens.VehicleRegistrationScreen
import com.example.appmudanza.ui.theme.screens.ConductorDetalladoScreen
import com.example.appmudanza.ui.theme.screens.SettingsScreen
import com.example.appmudanza.viewmodel.VehicleViewModel


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
                },

                onConductorClick = { index ->
                navController.navigate("conductorDetallado/$index") // ✅ así reemplaza el valor
            }
            )
        }
        composable("conductorDetallado/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0
            val vehicleViewModel: VehicleViewModel = viewModel()
            val vehicles by vehicleViewModel.vehicles.collectAsState()
            val conductores = vehicles.filter {it.withDriver}

            if (conductores.isNotEmpty() && index <conductores.size) {
                ConductorDetalladoScreen(
                    vehicle = conductores[index],
                    onBack = {navController.popBackStack()}
                )
            }

        }
        composable(Route.Incidencias.path) {
            IncidenciasScreen(
                onBack = {
                    navController.popBackStack()
                },
                onMudanzaClick = { mudanza ->
                    navController.navigate("detalle_incidencia/${mudanza.titulo}")
                }
            )
        }

        composable("detalle_incidencia/{titulo}") { backStackEntry ->
            val titulo = backStackEntry.arguments?.getString("titulo") ?: "Detalle incidencia"

            DetalleIncidenciaScreen(
                titulo = titulo,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Settings.path) {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}