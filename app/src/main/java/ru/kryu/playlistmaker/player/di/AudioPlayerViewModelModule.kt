package ru.kryu.playlistmaker.player.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.player.ui.view_model.AudioPlayerViewModel

val audioPlayerViewModelModule = module {
    viewModel { (trackUrl: String) ->
        AudioPlayerViewModel(trackUrl, get())
    }
}