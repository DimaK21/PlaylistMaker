package ru.kryu.playlistmaker.player.di

import org.koin.dsl.module

val playerModule = module {
    includes(
        playerDataModule,
        playerRepositoryModule,
        playerInteractorModule,
        audioPlayerViewModelModule
    )
}