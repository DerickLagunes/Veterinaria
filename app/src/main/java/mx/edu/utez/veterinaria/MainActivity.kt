package mx.edu.utez.veterinaria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.veterinaria.ui.Navigation
import mx.edu.utez.veterinaria.ui.theme.VeterinariaTheme
import mx.edu.utez.veterinaria.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // 1. obtener la instancia del viewModel
            val themeViewModel: ThemeViewModel = viewModel()
            // 2. Recolecta el estado
            val isDarkMode = themeViewModel.isDark.collectAsState()
            // 3. Inicializarl el themeViewmodel
            val systemIsDark = isSystemInDarkTheme()
            LaunchedEffect(Unit) {
                themeViewModel.loadInitialTheme(systemIsDark)
            }

            VeterinariaTheme (darkTheme = isDarkMode.value) {
                Navigation(
                    navController = rememberNavController(),
                    isDarkMode = isDarkMode.value,
                    onToggleTheme = { themeViewModel.toggleTheme() }
                )
            }
        }
    }
}
