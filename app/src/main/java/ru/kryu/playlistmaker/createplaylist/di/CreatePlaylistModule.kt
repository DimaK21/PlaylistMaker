package ru.kryu.playlistmaker.createplaylist.di

import org.koin.dsl.module

val createPlaylistModule = module {
    includes(
        createPlaylistDataModule,
        createPlaylistRepositoryModule,
        createPlaylistInteractorModule,
        createPlaylistViewModelModule,
    )
}