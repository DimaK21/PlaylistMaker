package ru.kryu.playlistmaker.player.di

import android.media.MediaPlayer
import org.koin.dsl.module
import ru.kryu.playlistmaker.player.data.Player
import ru.kryu.playlistmaker.player.data.player.AndroidMediaPlayer

val playerDataModule = module {
    factory<Player> {
        AndroidMediaPlayer(mediaPlayer = get())
    }

    factory {
        MediaPlayer()
    }
}