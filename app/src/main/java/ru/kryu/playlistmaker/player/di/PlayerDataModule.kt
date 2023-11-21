package ru.kryu.playlistmaker.player.di

import android.media.MediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.player.data.Player
import ru.kryu.playlistmaker.player.data.player.AndroidMediaPlayer

@Module
@InstallIn(SingletonComponent::class)
class PlayerDataModule {
    @Provides
    fun providePlayer(mediaPlayer: MediaPlayer): Player =
        AndroidMediaPlayer(mediaPlayer = mediaPlayer)

    @Provides
    fun provideMediaPlayer(): MediaPlayer =
        MediaPlayer()
}