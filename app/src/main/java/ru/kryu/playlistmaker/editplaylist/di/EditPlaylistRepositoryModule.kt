package ru.kryu.playlistmaker.editplaylist.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.editplaylist.data.EditPlaylistRepositoryImpl
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistRepository

val editPlaylistRepositoryModule = module {
    single<EditPlaylistRepository> {
        EditPlaylistRepositoryImpl(database = get())
    }
}