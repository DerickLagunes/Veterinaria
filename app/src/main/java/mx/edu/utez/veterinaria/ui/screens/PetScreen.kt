package mx.edu.utez.veterinaria.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mx.edu.utez.calculadoramvvm.ui.components.texts.Label
import mx.edu.utez.veterinaria.R
import mx.edu.utez.veterinaria.data.model.Pet
import mx.edu.utez.veterinaria.ui.components.PetList
import mx.edu.utez.veterinaria.ui.components.texts.Title
import mx.edu.utez.veterinaria.viewmodel.PetViewModel
import java.io.File


@Composable
fun PetScreen(viewModel: PetViewModel, navController: NavController) {
    val pets by viewModel.petsUiState.collectAsState()
    val context = LocalContext.current

    // --- 1. ESTADO PARA GUARDAR LA URI DE LA IMAGEN ---
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // URI temporal solo para la cámara
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    // --- 2. LAUNCHER PARA LA GALERÍA ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    // --- 3. LAUNCHER PARA LA CÁMARA ---
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                selectedImageUri = cameraImageUri
            }
        }
    )

    // --- 4. LAUNCHER PARA EL PERMISO DE CÁMARA ---
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Si el permiso se concede, crea la URI y lanza la cámara
                val newUri = createImageUri(context)
                cameraImageUri = newUri
                cameraLauncher.launch(newUri)
            } else {
                // Opcional: Mostrar un mensaje al usuario
            }
        }
    )

    // --- 5. ESTADO PARA LOS CAMPOS DE TEXTO ---
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }

    // --- 6. LAYOUT ---
    Column(
        modifier = Modifier
            .fillMaxSize() // Usa fillMaxSize para que el Scroll funcione
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Mascotas Atendidas")

        // Mostramos la lista
        PetList(pets,modifier = Modifier.weight(1f))
        if (pets.isEmpty()) {
            Label("No hay mascotas registradas.")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider()
        Spacer(modifier = Modifier.height(24.dp))

        Title("Registrar Nueva Mascota")

        // --- 7. VISTA PREVIA DE LA IMAGEN ---
        AsyncImage(
            model = selectedImageUri,
            contentDescription = "Foto de la nueva mascota",
            placeholder = painterResource(id = R.drawable.vetlogo), // Placeholder
            error = painterResource(id = R.drawable.vetlogo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape) // Vista previa circular
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 8. BOTONES PARA SELECCIONAR IMAGEN ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                // Lanzamos el selector de galería
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Galería")
            }
            Button(onClick = {
                // Verificamos el permiso antes de lanzar la cámara
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text("Tomar Foto")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 9. CAMPOS DE TEXTO ---
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = species,
            onValueChange = { species = it },
            label = { Text("Especie:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = breed,
            onValueChange = { breed = it },
            label = { Text("Raza:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --- 10. BOTÓN DE GUARDAR ACTUALIZADO ---
        Button(
            onClick = {
                // Llamamos a la nueva función del ViewModel
                viewModel.addNewPet(name, species, breed, selectedImageUri)

                // Reseteamos los campos
                name = ""
                species = ""
                breed = ""
                selectedImageUri = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Mascota")
        }
    }
}

// --- Esta función auxiliar es importante para la cámara ---
private fun createImageUri(context: Context): Uri {
    val imageDir = File(context.cacheDir, "images")
    imageDir.mkdirs()
    val file = File(imageDir, "pet_photo_${System.currentTimeMillis()}.jpg")
    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}

