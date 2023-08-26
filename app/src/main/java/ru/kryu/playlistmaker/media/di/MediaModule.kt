package ru.kryu.playlistmaker.media.di

import org.koin.dsl.module

val mediaModule = module {
    includes(
        mediaViewModelModule,

    )
}