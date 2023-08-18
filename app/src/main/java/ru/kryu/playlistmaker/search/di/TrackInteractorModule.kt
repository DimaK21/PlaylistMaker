package ru.kryu.playlistmaker.search.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.impl.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.search.domain.impl.TrackSearchInteractorImpl
import java.util.concurrent.Executors

val trackInteractorModule = module {
    single<TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(repository = get())
    }
    single<TrackSearchInteractor> {
        TrackSearchInteractorImpl(repository = get(), executor = get())
    }
    single {
        Executors.newCachedThreadPool()
    }
}