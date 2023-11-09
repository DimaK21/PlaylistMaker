package ru.kryu.playlistmaker.playlistmain.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.playlistmain.ui.viewmodel.PlaylistMainViewModel

val playlistMainViewModelModule = module {
    viewModel { (playlistId: Long) ->
        PlaylistMainViewModel(playlistId = playlistId)
    }
}