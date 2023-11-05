package ru.kryu.playlistmaker.favourite.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesInteractor
import ru.kryu.playlistmaker.favourite.domain.impl.FavouritesInteractorImpl

val favouriteInteractorModule = module {
    single<FavouritesInteractor> {
        FavouritesInteractorImpl(favouritesRepository = get())
    }
}