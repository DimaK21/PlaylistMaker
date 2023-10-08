package ru.kryu.playlistmaker.favourite.di

import org.koin.dsl.module

val favouriteModule = module {
    includes(
        favouriteViewModelModule,
        favouriteDataModule,
        favouriteRepositoryModule,
        favouriteInteractorModule,
    )
}