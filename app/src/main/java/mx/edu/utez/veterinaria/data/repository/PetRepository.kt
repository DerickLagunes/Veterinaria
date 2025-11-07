package mx.edu.utez.veterinaria.data.repository

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mx.edu.utez.veterinaria.data.model.Pet
//import mx.edu.utez.veterinaria.data.model.dao.PetDao
import mx.edu.utez.veterinaria.data.network.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

class PetRepository(
    private val apiService: ApiService,
    // Necesitamos el Context para leer el archivo de la imagen
    private val context: Context
) {

    suspend fun getPets(): List<Pet> {
        return try {
            apiService.getPets()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Cambiamos la firma para aceptar la Uri de la imagen
    suspend fun insertPet(name: String, species: String, breed: String, imageUri: Uri?) {
        try {
            // 1. Convertir los Strings a RequestBody
            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val speciesBody = species.toRequestBody("text/plain".toMediaTypeOrNull())
            val breedBody = breed.toRequestBody("text/plain".toMediaTypeOrNull())

            // 2. Convertir la Uri de la imagen a MultipartBody.Part
            var imagePart: MultipartBody.Part? = null
            if (imageUri != null) {
                // Obtenemos el tipo de contenido (ej. "image/jpeg")
                val type = context.contentResolver.getType(imageUri)
                val stream: InputStream? = context.contentResolver.openInputStream(imageUri)
                val bytes = stream?.readBytes()
                stream?.close()

                if (bytes != null && type != null) {
                    val requestFile = bytes.toRequestBody(type.toMediaTypeOrNull())
                    // 'image' debe coincidir con el nombre en el backend (request.files.get('image'))
                    imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)
                }
            }

            // 3. Llamar a la API
            apiService.addPet(
                name = nameBody,
                species = speciesBody,
                breed = breedBody,
                image = imagePart
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}