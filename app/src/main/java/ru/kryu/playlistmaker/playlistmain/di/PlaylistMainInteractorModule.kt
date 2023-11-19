package ru.kryu.playlistmaker.playlistmain.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.domain.impl.PlaylistMainInteractorImpl

val playlistMainInteractorModule = module {
    single<PlaylistMainInteractor> {
        PlaylistMainInteractorImpl(playlistMainRepository = get())
    }
}