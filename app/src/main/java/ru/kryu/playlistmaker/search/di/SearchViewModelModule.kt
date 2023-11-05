package ru.kryu.playlistmaker.search.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.search.ui.viewmodel.SearchViewModel

val searchViewModelModule = module {
    viewModel {
        SearchViewModel(
            application = androidApplication(),
            trackSearchInteractor = get(),
            trackHistoryInteractor = get(),
        )
    }
}