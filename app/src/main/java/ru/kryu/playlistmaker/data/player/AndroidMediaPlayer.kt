package ru.kryu.playlistmaker.data.player

import android.media.MediaPlayer
import ru.kryu.playlistmaker.data.Player
import ru.kryu.playlistmaker.domain.api.PlayerInteractor

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

    override fun setOnPreparedListener(preparedListener: PlayerInteractor.PreparedListener) {
        mediaPlayer.setOnPreparedListener {
            preparedListener.setOnPreparedListener()
        }
    }

    override fun setOnCompletionListener(completionListener: PlayerInteractor.CompletionListener) {
        mediaPlayer.setOnCompletionListener {
            completionListener.setOnCompletionListener()
        }
    }
}