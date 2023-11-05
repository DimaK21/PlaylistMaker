package ru.kryu.playlistmaker.playlists.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.playlists.ui.viewmodel.PlaylistsViewModel

val playlistsViewModelModule = module {
    viewModel {
        PlaylistsViewModel(playlistsInteractor = get())
    }
}