package ru.kryu.playlistmaker.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.search.data.HistoryStorage
import ru.kryu.playlistmaker.search.data.NetworkClient
import ru.kryu.playlistmaker.search.data.TrackHistoryRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.model.Track
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchRepositoryModule {
    @Provides
    fun provideTrackSearchRepository(
        networkClient: NetworkClient,
        database: AppDatabase,
    ): TrackSearchRepository =
        TrackSearchRepositoryImpl(networkClient = networkClient, database = database)

    @Provides
    fun provideTrackHistoryRepository(
        historyStorage: HistoryStorage,
        trackHistory: MutableList<Track>,
        database: AppDatabase,
    ): TrackHistoryRepository =
        TrackHistoryRepositoryImpl(
            historyStorage = historyStorage,
            trackHistory = trackHistory,
            database = database
        )

    @Provides
    @Singleton
    fun provideTrackHistory(): MutableList<Track> = mutableListOf<Track>()
}