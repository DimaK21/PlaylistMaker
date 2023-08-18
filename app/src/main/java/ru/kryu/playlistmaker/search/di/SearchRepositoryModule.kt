package ru.kryu.playlistmaker.search.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.search.data.TrackHistoryRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.model.Track

val searchRepositoryModule = module {
    single<TrackSearchRepository> {
        TrackSearchRepositoryImpl(networkClient = get())
    }
    single<TrackHistoryRepository> {
        TrackHistoryRepositoryImpl(historyStorage = get(), trackHistory = get())
    }
    single {
        mutableListOf<Track>()
    }
}