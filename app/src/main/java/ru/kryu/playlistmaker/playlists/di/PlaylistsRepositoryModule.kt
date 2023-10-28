package ru.kryu.playlistmaker.playlists.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.createplaylist.data.CreatePlaylistRepositoryImpl
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.playlists.data.PlaylistsRepositoryImpl
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsRepository

val playlistsRepositoryModule = module {
    single<PlaylistsRepository> {
        PlaylistsRepositoryImpl(database = get())
    }
}