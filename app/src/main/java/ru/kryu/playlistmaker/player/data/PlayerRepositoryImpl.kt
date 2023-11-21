package ru.kryu.playlistmaker.player.data

import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(private val player: Player) : PlayerRepository {

    override fun preparePlayer(url: String) {
        player.preparePlayer(url)
    }

    override fun startPlayer() {
        player.startPlayer()
    }

    override fun pausePlayer() {
        player.pausePlayer()
    }

    override fun stopPlayer() {
        player.stopPlayer()
    }

    override fun currentPosition(): Int {
        return player.currentPosition()
    }

    override fun setOnPreparedListener(preparedListener: PlayerInteractor.PreparedListener) {
        player.setOnPreparedListener(preparedListener)
    }

    override fun setOnCompletionListener(completionListener: PlayerInteractor.CompletionListener) {
        player.setOnCompletionListener(completionListener)
    }
}