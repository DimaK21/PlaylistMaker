package ru.kryu.playlistmaker.search.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.impl.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.search.domain.impl.TrackSearchInteractorImpl
import ru.kryu.playlistmaker.settings.di.SharedPreferencesSearchDataModule

@Module
@InstallIn(ViewModelComponent::class)
class TrackInteractorModule {

    @Provides
    fun provideTrackHistoryInteractor(repository: TrackHistoryRepository): TrackHistoryInteractor =
        TrackHistoryInteractorImpl(repository = repository)

    @Provides
    fun provideTrackSearchInteractor(repository: TrackSearchRepository): TrackSearchInteractor =
        TrackSearchInteractorImpl(repository = repository)
}

