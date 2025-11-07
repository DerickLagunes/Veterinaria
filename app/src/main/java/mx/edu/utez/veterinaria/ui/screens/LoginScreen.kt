package mx.edu.utez.veterinaria.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import mx.edu.utez.calculadoramvvm.ui.components.images.CircularImage
import mx.edu.utez.calculadoramvvm.ui.components.texts.Link
import mx.edu.utez.veterinaria.R
import mx.edu.utez.veterinaria.ui.components.buttons.DarkModeSwitch
import mx.edu.utez.veterinaria.ui.components.buttons.PrimaryButton
import mx.edu.utez.veterinaria.ui.components.inputs.PasswordField
import mx.edu.utez.veterinaria.ui.components.inputs.UserInputField
import mx.edu.utez.veterinaria.ui.components.texts.Title
import mx.edu.utez.veterinaria.ui.theme.VeterinariaTheme
import mx.edu.utez.veterinaria.viewmodel.LoginViewModel
import kotlin.text.isNotEmpty

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController,
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(30.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        CircularImage(R.drawable.vetlogo,200)
        Title("Vet\nCare")

        UserInputField(
            viewModel = viewModel,
            label = "Usuario"
        )

        PasswordField(
            viewModel = viewModel,
            label = "Contraseña"
        )

        if (viewModel.loginError.value.isNotEmpty()) {
            Text(
                text = viewModel.loginError.value,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Link("¿Has olvidado la contraseña?") {
            navController.navigate("forgot_password")
        }

        PrimaryButton("Iniciar sesión") {
            viewModel.login {
                navController.navigate("petMain") {
                    popUpTo("login") { inclusive = true } // Evita volver al login
                }
            }
        }

        Link("¿No tienes cuenta? Regístrate") {
            navController.navigate("register")
        }

        DarkModeSwitch(
            isDarkMode,
            {onToggleTheme()},
            Modifier
        )

    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    VeterinariaTheme {
        val navController = rememberNavController()
        val viewModel = LoginViewModel()
        val systemIsDark = isSystemInDarkTheme()
        var isDarkMode by remember { mutableStateOf(systemIsDark) }

        LoginScreen(
            viewModel = viewModel,
            navController = navController,
            isDarkMode,
            {}
        )
    }
}
