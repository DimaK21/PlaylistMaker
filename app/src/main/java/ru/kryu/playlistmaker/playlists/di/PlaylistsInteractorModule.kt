package ru.kryu.playlistmaker.playlists.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsRepository
import ru.kryu.playlistmaker.playlists.domain.impl.PlaylistsInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class PlaylistsInteractorModule {
    @Provides
    fun providePlaylistsInteractor(playlistsRepository: PlaylistsRepository): PlaylistsInteractor =
        PlaylistsInteractorImpl(playlistsRepository = playlistsRepository)
}