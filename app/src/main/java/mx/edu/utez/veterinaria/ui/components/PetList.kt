package mx.edu.utez.veterinaria.ui.components

import android.R.attr.x
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.veterinaria.data.model.Pet

@Composable
fun PetList(lista: List<Pet>, modifier: Modifier = Modifier) { // <-- Añade modifier
    LazyColumn(
        modifier = modifier, // <-- Usa el modifier que te pasen
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = lista, key = { it.id }) { pet ->
            PetCard(pet)
        }
    }
}