package ru.kryu.playlistmaker.createplaylist.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.createplaylist.data.ImageStorage
import ru.kryu.playlistmaker.createplaylist.data.storage.ExternalPrivateStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CreatePlaylistDataModule {
    @Provides
    @Singleton
    fun provideExternalPrivateStorage(@ApplicationContext context: Context): ImageStorage =
        ExternalPrivateStorage(context = context)
}