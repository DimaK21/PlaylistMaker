package ru.kryu.playlistmaker.playlist.di

import org.koin.dsl.module

val playlistModule = module {
    includes(
        playlistViewModelModule,

        )
}