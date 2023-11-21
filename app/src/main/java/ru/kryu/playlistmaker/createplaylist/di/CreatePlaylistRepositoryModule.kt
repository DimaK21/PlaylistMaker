package ru.kryu.playlistmaker.createplaylist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.createplaylist.data.CreatePlaylistRepositoryImpl
import ru.kryu.playlistmaker.createplaylist.data.ImageStorage
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CreatePlaylistRepositoryModule {
    @Provides
    @Singleton
    fun provideCreatePlaylistRepository(
        database: AppDatabase,
        imageStorage: ImageStorage
    ): CreatePlaylistRepository =
        CreatePlaylistRepositoryImpl(database = database, imageStorage = imageStorage)
}