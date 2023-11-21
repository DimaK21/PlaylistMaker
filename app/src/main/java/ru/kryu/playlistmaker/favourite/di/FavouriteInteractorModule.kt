package ru.kryu.playlistmaker.favourite.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesInteractor
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesRepository
import ru.kryu.playlistmaker.favourite.domain.impl.FavouritesInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class FavouriteInteractorModule {
    @Provides
    fun provideFavouritesInteractor(favouritesRepository: FavouritesRepository): FavouritesInteractor =
        FavouritesInteractorImpl(favouritesRepository = favouritesRepository)
}
