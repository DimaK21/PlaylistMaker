package ru.kryu.playlistmaker.createplaylist.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.createplaylist.domain.impl.CreatePlaylistInteractorImpl

val createPlaylistInteractorModule = module {
    single<CreatePlaylistInteractor> {
        CreatePlaylistInteractorImpl(get())
    }
}