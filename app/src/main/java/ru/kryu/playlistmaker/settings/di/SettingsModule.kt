package ru.kryu.playlistmaker.settings.di

import org.koin.dsl.module

val settingsModule = module {
    includes(
        settingsDataModule,
        settingsRepositoryModule,
        settingsInteractorModule,
        settingsViewModelModule
    )
}