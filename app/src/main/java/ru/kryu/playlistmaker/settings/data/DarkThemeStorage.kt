package ru.kryu.playlistmaker.settings.data

interface DarkThemeStorage {
    fun getDarkTheme(): Boolean
    fun saveDarkTheme(isDarkTheme: Boolean)
}