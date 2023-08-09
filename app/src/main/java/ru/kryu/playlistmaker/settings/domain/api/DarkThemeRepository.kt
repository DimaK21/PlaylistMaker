package ru.kryu.playlistmaker.settings.domain.api

interface DarkThemeRepository {
    fun getDarkTheme(): Boolean
    fun saveDarkTheme(isDarkTheme: Boolean)
}
