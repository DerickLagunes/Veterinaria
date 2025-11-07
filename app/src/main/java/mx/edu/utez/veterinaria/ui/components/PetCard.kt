package mx.edu.utez.veterinaria.ui.components

import android.R.attr.x
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import mx.edu.utez.veterinaria.R
import mx.edu.utez.veterinaria.data.model.Pet

@Composable
fun PetCard(pet: Pet, modifier: Modifier = Modifier) {

    // El color de fondo de la página del pasaporte
    val petBgColor = Color(0xFFF5F8F0)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable{},
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = petBgColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // --- 1. Encabezado ---
            PetHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // --- 2. Contenido Principal (Foto y Datos) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // --- Columna Izquierda (Foto y Firma) ---
                Column(
                    modifier = Modifier.weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- USA AsyncImage PARA CARGAR DESDE LA URL ---
                    AsyncImage(
                        model = pet.imageUrl, // La URL que viene de tu API
                        contentDescription = "Foto de ${pet.name}",
                        // Placeholder mientras carga o si hay error
                        placeholder = painterResource(id = R.drawable.vetlogo),
                        error = painterResource(id = R.drawable.vetlogo),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(3f / 4f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                }

                Spacer(modifier = Modifier.width(16.dp))

                // --- Columna Derecha (Datos) ---
                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    DataField("Identificador de macota:", pet.id.toString())
                    DataField("Nombre:", pet.name.uppercase())
                    DataField("Especie:", pet.species)
                    DataField("Raza:", pet.breed)
                }
            }
        }
    }
}

@Composable
private fun PetHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.vetlogo),
            contentDescription = "Escudo de vet",
            modifier = Modifier.size(50.dp)
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "MASCOTA",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 2.sp
            )
            Text(
                text = "VET - CARE",
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
private fun DataField(
    label: String,
    value: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 6.dp)) {
        Text(
            text = label.uppercase(),
            fontSize = 9.sp,
            color = Color.Gray,
            letterSpacing = 0.5.sp
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}