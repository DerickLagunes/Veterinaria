package mx.edu.utez.veterinaria.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.edu.utez.veterinaria.data.PreferenceManager
import mx.edu.utez.veterinaria.data.repository.ThemeRepository

class ThemeViewModel(application: Application): AndroidViewModel(application) {

    private val themeRepository = ThemeRepository(application)
    private val _isDark = MutableStateFlow(false)
    val isDark = _isDark.asStateFlow()

    fun loadInitialTheme(systemIsDark: Boolean){
        viewModelScope.launch {
            themeRepository.themeFlow.collect { savedTheme ->
                _isDark.value = savedTheme ?: systemIsDark
            }
        }
    }

    fun toggleTheme(){
        val newValue = !_isDark.value
        viewModelScope.launch {
            themeRepository.saveTheme(newValue)
        }
    }

}