package mx.edu.utez.veterinaria.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.veterinaria.ui.screens.LoginScreen
import mx.edu.utez.veterinaria.viewmodel.LoginViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(
                viewModel = viewModel,
                navController = navController,
                isDarkMode = isDarkMode,
                onToggleTheme = onToggleTheme)
        }
    }
}