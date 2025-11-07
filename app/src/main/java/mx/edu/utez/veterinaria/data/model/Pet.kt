package mx.edu.utez.veterinaria.data.model


data class Pet(
    val id: Int = 0,
    val name: String,
    val species: String,
    val breed: String,
    val imageUrl: String?
)