package ru.kryu.playlistmaker.createplaylist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.createplaylist.ui.viewmodel.CreatePlaylistViewModel

val createPlaylistViewModelModule = module {
    viewModel {
        CreatePlaylistViewModel()
    }
}