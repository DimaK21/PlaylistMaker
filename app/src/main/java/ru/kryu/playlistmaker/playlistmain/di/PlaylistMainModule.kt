package ru.kryu.playlistmaker.playlistmain.di

import org.koin.dsl.module

val playlistMainModule = module {
    includes(
        playlistMainViewModelModule,
    )
}