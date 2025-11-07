package mx.edu.utez.veterinaria.data.network

import mx.edu.utez.veterinaria.data.model.Pet
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("pets")
    suspend fun getPets(): List<Pet>

    @Multipart // 1. Marcamos como Multipart
    @POST("pets")
    suspend fun addPet(
        // 2. Usamos @Part para cada campo de texto
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("breed") breed: RequestBody,
        // 3. Usamos @Part para el archivo (si existe)
        @Part image: MultipartBody.Part?
    ): Pet
}