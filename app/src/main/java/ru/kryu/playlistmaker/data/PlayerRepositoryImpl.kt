package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.domain.api.PlayerRepository
import ru.kryu.playlistmaker.domain.api.PlayerInteractor

class PlayerRepositoryImpl(private val player: Player) : PlayerRepository {

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