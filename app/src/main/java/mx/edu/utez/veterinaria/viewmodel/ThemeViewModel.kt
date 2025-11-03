package mx.edu.utez.veterinaria.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.edu.utez.veterinaria.data.PreferenceManager

class ThemeViewModel(application: Application): AndroidViewModel(application) {

    private val preferenceManager = PreferenceManager(application)
    private val _isDark = MutableStateFlow(false)
    val isDark = _isDark.asStateFlow()

    fun loadInitialTheme(systemIsDark: Boolean){
        val savedTheme: Boolean? = preferenceManager.loadTheme()
        _isDark.value = savedTheme ?: systemIsDark
    }

    fun toggleTheme(){
        val newValue = !_isDark.value
        _isDark.value = newValue

        preferenceManager.saveTheme(newValue)
    }
}