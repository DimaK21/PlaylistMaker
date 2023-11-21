package ru.kryu.playlistmaker.settings.data

import ru.kryu.playlistmaker.settings.domain.api.DarkThemeRepository
import javax.inject.Inject

class DarkThemeRepositoryImpl @Inject constructor(private val darkThemeStorage: DarkThemeStorage) :
    DarkThemeRepository {
    override fun getDarkTheme(): Boolean {
        return darkThemeStorage.getDarkTheme()
    }

    override fun saveDarkTheme(isDarkTheme: Boolean) {
        darkThemeStorage.saveDarkTheme(isDarkTheme)
    }
}