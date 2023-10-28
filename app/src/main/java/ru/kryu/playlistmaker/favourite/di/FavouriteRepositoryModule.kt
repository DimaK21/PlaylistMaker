package ru.kryu.playlistmaker.favourite.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.favourite.data.FavouritesRepositoryImpl
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesRepository

val favouriteRepositoryModule = module {
    single<FavouritesRepository> {
        FavouritesRepositoryImpl(get())
    }
}