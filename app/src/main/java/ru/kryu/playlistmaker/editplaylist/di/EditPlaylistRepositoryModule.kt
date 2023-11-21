package ru.kryu.playlistmaker.editplaylist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.editplaylist.data.EditPlaylistRepositoryImpl
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistRepository
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EditPlaylistRepositoryModule {
    @Provides
    @Singleton
    fun provideEditPlaylistRepository(
        database: AppDatabase
    ): EditPlaylistRepository =
        EditPlaylistRepositoryImpl(database = database)
}
