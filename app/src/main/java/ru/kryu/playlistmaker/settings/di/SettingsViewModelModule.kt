package ru.kryu.playlistmaker.settings.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.settings.ui.view_model.SettingsViewModel

val settingsViewModelModule = module {
    viewModel {
        SettingsViewModel(
            actionSend = get(),
            actionSendTo = get(),
            actionView = get(),
            darkThemeInteractor = get()
        )
    }
}