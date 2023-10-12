package ru.kryu.playlistmaker.favourite.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.favourite.ui.view_model.FavouritesViewModel

val favouriteViewModelModule = module {
    viewModel {
        FavouritesViewModel(favouritesInteractor = get())
    }
}