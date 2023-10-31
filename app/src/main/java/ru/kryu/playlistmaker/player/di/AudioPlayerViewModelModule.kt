package ru.kryu.playlistmaker.player.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kryu.playlistmaker.player.ui.viewmodel.AudioPlayerViewModel
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

val audioPlayerViewModelModule = module {
    viewModel { (track: TrackForUi) ->
        AudioPlayerViewModel(
            track = track,
            mediaPlayerInteractor = get(),
            favouritesInteractor = get(),
            playlistsInteractor = get(),
            trackInPlaylistInteractor = get(),
        )
    }
}