package ru.kryu.playlistmaker.settings.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.settings.data.DarkThemeRepositoryImpl
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeRepository

val settingsRepositoryModule = module {
    single<DarkThemeRepository> {
        DarkThemeRepositoryImpl(darkThemeStorage = get())
    }
}