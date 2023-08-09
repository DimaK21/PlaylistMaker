package ru.kryu.playlistmaker.settings.data

import ru.kryu.playlistmaker.settings.domain.api.DarkThemeRepository

class DarkThemeRepositoryImpl(private val darkThemeStorage: DarkThemeStorage): DarkThemeRepository {
    override fun getDarkTheme(): Boolean {
        return darkThemeStorage.getDarkTheme()
    }

    override fun saveDarkTheme(isDarkTheme: Boolean) {
        darkThemeStorage.saveDarkTheme(isDarkTheme)
    }
}