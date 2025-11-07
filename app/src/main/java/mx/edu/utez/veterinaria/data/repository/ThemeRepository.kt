package mx.edu.utez.veterinaria.data.repository

import android.content.Context
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import mx.edu.utez.veterinaria.data.PrefKeys
import mx.edu.utez.veterinaria.data.dataStore
import okio.IOException

class ThemeRepository(context: Context) {

    private val dataStore = context.dataStore

    val themeFlow: Flow<Boolean?> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PrefKeys.IS_DARK_MODE]
        }

    suspend fun saveTheme(isDark: Boolean){
        dataStore.edit { preferences ->
            preferences[PrefKeys.IS_DARK_MODE] = isDark
        }
    }
}