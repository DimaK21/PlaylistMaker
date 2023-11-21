package ru.kryu.playlistmaker.createplaylist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.createplaylist.domain.impl.CreatePlaylistInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class CreatePlaylistInteractorModule {
    @Provides
    fun provideCreatePlaylistInteractor(createPlaylistRepository: CreatePlaylistRepository): CreatePlaylistInteractor =
        CreatePlaylistInteractorImpl(createPlaylistRepository = createPlaylistRepository)
}