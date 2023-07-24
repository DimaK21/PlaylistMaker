package ru.kryu.playlistmaker.data.player

import android.media.MediaPlayer
import ru.kryu.playlistmaker.data.Player
import ru.kryu.playlistmaker.domain.api.PlayerUseCase

class AndroidMediaPlayer : Player {

    private val mediaPlayer = MediaPlayer()

    override fun preparePlayer(dto: Any) {
        mediaPlayer.setDataSource(dto as String)
        mediaPlayer.prepareAsync()
    }

    override fun startPlayer() {
        mediaPlayer.start()
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
    }

    override fun stopPlayer() {
        mediaPlayer.release()
    }

    override fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun setOnPreparedListener(preparedListener: PlayerUseCase.PreparedListener) {
        mediaPlayer.setOnPreparedListener {
            preparedListener.setOnPreparedListener()
        }
    }

    override fun setOnCompletionListener(completionListener: PlayerUseCase.CompletionListener) {
        mediaPlayer.setOnCompletionListener {
            completionListener.setOnCompletionListener()
        }
    }
}