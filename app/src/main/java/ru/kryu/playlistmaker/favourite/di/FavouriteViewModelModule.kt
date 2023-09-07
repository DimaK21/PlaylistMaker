package ru.kryu.playlistmaker.favourite.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.favourite.ui.view_model.FavouritesViewModel
import ru.kryu.playlistmaker.favourite.ui.view_model.PlaylistsViewModel

val favouriteViewModelModule = module {
    viewModel {
        FavouritesViewModel()
    }
    viewModel {
        PlaylistsViewModel()
    }
}