package ru.kryu.playlistmaker.editplaylist.di

import org.koin.dsl.module

val editPlaylistModule = module {
    includes(
        editPlaylistViewModelModule
    )
}