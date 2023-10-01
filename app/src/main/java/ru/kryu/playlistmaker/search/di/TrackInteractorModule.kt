package ru.kryu.playlistmaker.search.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.impl.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.search.domain.impl.TrackSearchInteractorImpl

val trackInteractorModule = module {
    factory<TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(repository = get())
    }
    factory<TrackSearchInteractor> {
        TrackSearchInteractorImpl(repository = get())
    }
}