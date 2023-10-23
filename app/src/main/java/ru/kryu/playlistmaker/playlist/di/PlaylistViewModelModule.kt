package ru.kryu.playlistmaker.playlist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.playlist.ui.viewmodel.PlaylistsViewModel

val playlistViewModelModule = module {
    viewModel {
        PlaylistsViewModel()
    }
}