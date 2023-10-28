package ru.kryu.playlistmaker.createplaylist.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.createplaylist.data.CreatePlaylistRepositoryImpl
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository

val createPlaylistRepositoryModule = module {
    single<CreatePlaylistRepository> {
        CreatePlaylistRepositoryImpl(get())
    }
}