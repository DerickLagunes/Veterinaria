package mx.edu.utez.veterinaria.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "theme_prefs"
)

object PrefKeys{
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
}