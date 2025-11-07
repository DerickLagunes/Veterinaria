package mx.edu.utez.veterinaria.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utez.veterinaria.data.model.Pet
import mx.edu.utez.veterinaria.data.repository.PetRepository


class PetViewModel(private val repository: PetRepository) : ViewModel() {

    // Usamos MutableStateFlow para poder actualizar la lista manualmente
    private val _petsUiState = MutableStateFlow<List<Pet>>(emptyList())
    val petsUiState = _petsUiState.asStateFlow() // La UI observa esto

    // init se llama cuando el ViewModel se crea
    init {
        fetchPets() // Cargamos las mascotas al iniciar
    }

    // Función para obtener las mascotas
    fun fetchPets() {
        viewModelScope.launch {
            _petsUiState.value = repository.getPets()
        }
    }

    // Función para añadir una nueva mascota
    fun addNewPet(name: String, species: String, breed: String, imageUri: Uri?) {
        viewModelScope.launch {
            repository.insertPet(name, species, breed, imageUri)
            fetchPets() // Refrescar la lista
        }
    }
}

class PetViewModelFactory(
    private val repository: PetRepository,
    private val context: Context // <-- AÑADIR CONTEXT
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Pasamos el repositorio (que ya tiene el context)
            return PetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}