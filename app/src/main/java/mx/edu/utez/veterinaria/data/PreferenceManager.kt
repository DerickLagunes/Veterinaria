package mx.edu.utez.veterinaria.data

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "theme_prefs",
        Context.MODE_PRIVATE
    )

    private val KEY_IS_DARK_MODE = "is_dark_mode"

    fun saveTheme(isDark: Boolean){
        prefs.edit().putBoolean(KEY_IS_DARK_MODE, isDark).apply()
    }

    fun loadTheme(): Boolean? {
        if (!prefs.contains(KEY_IS_DARK_MODE)){
            return null
        }
        return  prefs.getBoolean(KEY_IS_DARK_MODE, false)
    }

}