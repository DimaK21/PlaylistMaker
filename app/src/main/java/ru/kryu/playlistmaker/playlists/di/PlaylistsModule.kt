package ru.kryu.playlistmaker.playlists.di

import org.koin.dsl.module

val playlistsModule = module {
    includes(
        playlistsRepositoryModule,
        playlistsInteractorModule,
        playlistsViewModelModule,
    )
}