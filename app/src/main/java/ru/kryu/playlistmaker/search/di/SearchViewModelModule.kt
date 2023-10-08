package ru.kryu.playlistmaker.search.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiToDomain
import ru.kryu.playlistmaker.search.ui.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.search.ui.view_model.SearchViewModel

val searchViewModelModule = module {
    viewModel {
        SearchViewModel(
            application = androidApplication(),
            trackSearchInteractor = get(),
            trackHistoryInteractor = get(),
            trackForUiToDomain = get(),
            trackToTrackForUi = get(),
        )
    }

    single {
        TrackForUiToDomain()
    }

    single {
        TrackToTrackForUi()
    }
}