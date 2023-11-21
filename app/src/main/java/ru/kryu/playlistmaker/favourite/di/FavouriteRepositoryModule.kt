package ru.kryu.playlistmaker.favourite.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.favourite.data.FavouritesRepositoryImpl
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouriteRepositoryModule {
    @Provides
    @Singleton
    fun provideFavouritesRepository(database: AppDatabase): FavouritesRepository =
        FavouritesRepositoryImpl(database = database)
}