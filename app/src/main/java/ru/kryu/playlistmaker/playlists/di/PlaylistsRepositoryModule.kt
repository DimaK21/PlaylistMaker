package ru.kryu.playlistmaker.playlists.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlists.data.PlaylistsRepositoryImpl
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlaylistsRepositoryModule {
    @Provides
    @Singleton
    fun providePlaylistsRepository(
        database: AppDatabase,
    ): PlaylistsRepository =
        PlaylistsRepositoryImpl(database = database)
}