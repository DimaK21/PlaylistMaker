package ru.kryu.playlistmaker.playlistmain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlistmain.data.PlaylistMainRepositoryImpl
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlaylistMainRepositoryModule {
    @Provides
    @Singleton
    fun providePlaylistMainRepository(
        database: AppDatabase,
    ): PlaylistMainRepository =
        PlaylistMainRepositoryImpl(database = database)
}