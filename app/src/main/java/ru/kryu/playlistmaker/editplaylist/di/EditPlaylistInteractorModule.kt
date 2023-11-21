package ru.kryu.playlistmaker.editplaylist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistInteractor
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistRepository
import ru.kryu.playlistmaker.editplaylist.domain.impl.EditPlaylistInteractorImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class EditPlaylistInteractorModule {
    @Provides
    fun provideEditPlaylistInteractor(editPlaylistRepository: EditPlaylistRepository): EditPlaylistInteractor =
        EditPlaylistInteractorImpl(editPlaylistRepository = editPlaylistRepository)
}