package ru.kryu.playlistmaker.editplaylist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.editplaylist.ui.viewmodel.EditPlaylistViewModel

val editPlaylistViewModelModule = module {
    viewModel {
        EditPlaylistViewModel(createPlaylistInteractor = get(), editPlaylistInteractor = get())
    }
}