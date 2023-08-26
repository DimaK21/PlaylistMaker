package ru.kryu.playlistmaker.media.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.media.ui.view_model.MediaViewModel

val mediaViewModelModule = module {
    viewModel {
        MediaViewModel()
    }
}