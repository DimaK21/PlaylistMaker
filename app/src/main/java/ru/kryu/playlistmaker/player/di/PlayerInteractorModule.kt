package ru.kryu.playlistmaker.player.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistInteractor
import ru.kryu.playlistmaker.player.domain.impl.PlayerInteractorImpl
import ru.kryu.playlistmaker.player.domain.impl.TrackInPlaylistInteractorImpl

val playerInteractorModule = module {

    factory<PlayerInteractor> {
        PlayerInteractorImpl(repository = get())
    }

    single<TrackInPlaylistInteractor> {
        TrackInPlaylistInteractorImpl(trackInPlaylistRepository = get())
    }
}