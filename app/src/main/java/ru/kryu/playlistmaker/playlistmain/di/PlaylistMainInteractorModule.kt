package ru.kryu.playlistmaker.playlistmain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository
import ru.kryu.playlistmaker.playlistmain.domain.impl.PlaylistMainInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class PlaylistMainInteractorModule {
    @Provides
    fun providePlaylistMainInteractor(playlistMainRepository: PlaylistMainRepository): PlaylistMainInteractor =
        PlaylistMainInteractorImpl(playlistMainRepository = playlistMainRepository)
}