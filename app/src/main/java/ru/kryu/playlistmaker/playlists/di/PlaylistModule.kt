package ru.kryu.playlistmaker.playlists.di

import org.koin.dsl.module

val playlistModule = module {
    includes(
        playlistViewModelModule,
    )
}