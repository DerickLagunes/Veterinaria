package mx.edu.utez.veterinaria.viewmodel

import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.edu.utez.veterinaria.data.model.Pet
import mx.edu.utez.veterinaria.data.repository.PetRepository


class PetViewModel(private val repository: PetRepository): ViewModel() {
    val petsUiState: StateFlow<List<Pet>> = repository.allPets
        .stateIn(
            scope = viewModelScope,
            started =  kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNewPet(name:String, species: String, breed: String){
        viewModelScope.launch {
            val newPet = Pet(name= name, species= species, breed= breed)
            repository.insertPet(newPet)
        }
    }
}

class PetViewModelFactory(
    private val repository: PetRepository
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        if(modelClass.isAssignableFrom((PetViewModel::class.java))){
            @Suppress("UNCHECKED_CAST")
            return PetViewModel(repository) as T
        }
        throw IllegalArgumentException("La clase no se conoce")
    }
}