package ru.kryu.playlistmaker.settings.domain.impl

import ru.kryu.playlistmaker.settings.domain.api.DarkThemeInteractor
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeRepository

class DarkThemeInteractorImpl(private val darkThemeRepository: DarkThemeRepository) :
    DarkThemeInteractor {
    override fun getDarkTheme(): Boolean {
        return darkThemeRepository.getDarkTheme()
    }

    override fun saveDarkTheme(isDarkTheme: Boolean) {
        darkThemeRepository.saveDarkTheme(isDarkTheme)
    }
}