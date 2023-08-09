package ru.kryu.playlistmaker.settings.data

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class SharedPrefsDarkTheme(private val context: Context): DarkThemeStorage {
    override fun getDarkTheme(): Boolean {
        val sharedPrefs = context.getSharedPreferences(USER_PREFERENCES, Application.MODE_PRIVATE)
        return sharedPrefs.getBoolean(DARK_THEME_KEY, false)
    }

    override fun saveDarkTheme(isDarkTheme: Boolean) {
        val sharedPrefs = context.getSharedPreferences(USER_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
        sharedPrefs.edit()
            .putBoolean(DARK_THEME_KEY, isDarkTheme)
            .apply()
    }

    companion object {
        const val USER_PREFERENCES = "user_preferences"
        const val DARK_THEME_KEY = "dark_theme_key"
    }
}