package ru.kryu.playlistmaker.playlists.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor
import ru.kryu.playlistmaker.playlists.domain.impl.PlaylistsInteractorImpl


val playlistsInteractorModule = module {
    single<PlaylistsInteractor> {
        PlaylistsInteractorImpl(playlistsRepository = get())
    }
}