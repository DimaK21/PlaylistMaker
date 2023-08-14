package ru.kryu.playlistmaker.settings.domain.api

interface DarkThemeInteractor {
    fun getDarkTheme(): Boolean
    fun saveDarkTheme(isDarkTheme: Boolean)
}