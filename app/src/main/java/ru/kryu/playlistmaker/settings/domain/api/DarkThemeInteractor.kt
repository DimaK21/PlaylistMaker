package ru.kryu.playlistmaker.settings.domain.api

import dagger.Provides

interface DarkThemeInteractor {
    fun getDarkTheme(): Boolean
    fun saveDarkTheme(isDarkTheme: Boolean)
}