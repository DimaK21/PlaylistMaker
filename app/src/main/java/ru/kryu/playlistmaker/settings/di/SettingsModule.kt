package ru.kryu.playlistmaker.settings.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.sharing.di.sharingModule

val settingsModule = module {
    includes(
        settingsDataModule,
        settingsRepositoryModule,
        settingsInteractorModule,
        settingsViewModelModule
    )
}