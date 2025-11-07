package mx.edu.utez.veterinaria.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import mx.edu.utez.veterinaria.data.model.VetDatabase
//import mx.edu.utez.veterinaria.data.model.dao.PetDao
import mx.edu.utez.veterinaria.data.network.RetrofitInstance
import mx.edu.utez.veterinaria.data.repository.PetRepository
import mx.edu.utez.veterinaria.ui.screens.LoginScreen
import mx.edu.utez.veterinaria.ui.screens.PetScreen
import mx.edu.utez.veterinaria.viewmodel.LoginViewModel
import mx.edu.utez.veterinaria.viewmodel.PetViewModel
import mx.edu.utez.veterinaria.viewmodel.PetViewModelFactory

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
        composable("petMain") {
            val context = LocalContext.current.applicationContext

            val petRepository = remember {
                // Pasamos el context al repositorio
                PetRepository(RetrofitInstance.api, context)
            }

            val factory = remember(petRepository, context) {
                // Pasamos el context a la factory
                PetViewModelFactory(petRepository, context)
            }

            val viewModel: PetViewModel = viewModel(factory = factory)

            // 4. Tu pantalla funciona sin cambios
            PetScreen(viewModel, navController)
        }
    }
}