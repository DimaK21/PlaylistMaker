package ru.kryu.playlistmaker.editplaylist.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistInteractor
import ru.kryu.playlistmaker.editplaylist.domain.impl.EditPlaylistInteractorImpl

val editPlaylistInteractorModule = module {
    single<EditPlaylistInteractor> {
        EditPlaylistInteractorImpl(editPlaylistRepository = get())
    }
}