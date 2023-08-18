package ru.kryu.playlistmaker.settings.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeInteractor
import ru.kryu.playlistmaker.settings.domain.impl.DarkThemeInteractorImpl

val settingsInteractorModule = module {
    single<DarkThemeInteractor> {
        DarkThemeInteractorImpl(darkThemeRepository = get())
    }
}