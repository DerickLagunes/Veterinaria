package mx.edu.utez.veterinaria.data.repository

import kotlinx.coroutines.flow.Flow
import mx.edu.utez.veterinaria.data.model.Pet
import mx.edu.utez.veterinaria.data.model.dao.PetDao

class PetRepository(private val petDao: PetDao) {
    val allPets: Flow<List<Pet>> = petDao.getAllPets()

    suspend fun insertPet(pet: Pet){
        petDao.insertPet(pet)
    }
}