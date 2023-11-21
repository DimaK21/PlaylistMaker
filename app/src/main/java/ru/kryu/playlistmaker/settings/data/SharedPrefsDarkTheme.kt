package ru.kryu.playlistmaker.settings.data

import android.content.SharedPreferences
import ru.kryu.playlistmaker.settings.di.SharedPreferencesSettingsDataModule
import javax.inject.Inject

class SharedPrefsDarkTheme @Inject constructor(@SharedPreferencesSettingsDataModule private val sharedPrefs: SharedPreferences) :
    DarkThemeStorage {
    override fun getDarkTheme(): Boolean {
        return sharedPrefs.getBoolean(DARK_THEME_KEY, false)
    }

    override fun saveDarkTheme(isDarkTheme: Boolean) {
        sharedPrefs.edit()
            .putBoolean(DARK_THEME_KEY, isDarkTheme)
            .apply()
    }

    companion object {
        const val DARK_THEME_KEY = "dark_theme_key"
    }
}