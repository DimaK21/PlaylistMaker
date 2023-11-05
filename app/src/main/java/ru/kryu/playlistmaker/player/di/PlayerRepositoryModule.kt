package ru.kryu.playlistmaker.player.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.player.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.player.data.TrackInPlaylistRepositoryImpl
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository

val playerRepositoryModule = module {

    factory<PlayerRepository> {
        PlayerRepositoryImpl(player = get())
    }

    single<TrackInPlaylistRepository> {
        TrackInPlaylistRepositoryImpl(database = get())
    }
}