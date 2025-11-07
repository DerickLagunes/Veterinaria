package mx.edu.utez.veterinaria.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mx.edu.utez.calculadoramvvm.ui.components.texts.Label
import mx.edu.utez.veterinaria.data.model.Pet
import mx.edu.utez.veterinaria.ui.components.PetList
import mx.edu.utez.veterinaria.ui.components.texts.Title
import mx.edu.utez.veterinaria.viewmodel.PetViewModel


@Composable
fun PetScreen(viewModel: PetViewModel, navController: NavController){
    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(33.dp)
    ) {
        Title("Mascotas Atendidas")
        val pets by viewModel.petsUiState.collectAsState()

        var name by remember { mutableStateOf("") }
        var species by remember { mutableStateOf("") }
        var breed by remember { mutableStateOf("") }

        PetList(pets)
        Label("No hay más pets")

        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = name,
            onValueChange = {name = it},
            label = {Text("Nombre:")}
        )
        TextField(
            value = species,
            onValueChange = {species = it},
            label = {Text("Especie:")}
        )
        TextField(
            value = breed,
            onValueChange = {breed = it},
            label = {Text("Raza:")}
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            viewModel.addNewPet(name,species,breed)
            name=""
            species=""
            breed=""
        }) {
            Text(text="Guardar Mascota")
        }
    }
}

