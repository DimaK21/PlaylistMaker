package ru.kryu.playlistmaker.player.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistInteractor
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository
import ru.kryu.playlistmaker.player.domain.impl.PlayerInteractorImpl
import ru.kryu.playlistmaker.player.domain.impl.TrackInPlaylistInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class PlayerInteractorModule {
    @Provides
    fun providePlayerInteractor(repository: PlayerRepository): PlayerInteractor =
        PlayerInteractorImpl(repository = repository)

    @Provides
    fun provideTrackInPlaylistInteractor(trackInPlaylistRepository: TrackInPlaylistRepository): TrackInPlaylistInteractor =
        TrackInPlaylistInteractorImpl(trackInPlaylistRepository = trackInPlaylistRepository)
}