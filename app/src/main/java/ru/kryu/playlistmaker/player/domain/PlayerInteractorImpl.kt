package ru.kryu.playlistmaker.player.domain

import ru.kryu.playlistmaker.player.domain.PlayerRepository
import ru.kryu.playlistmaker.player.domain.PlayerInteractor

class PlayerInteractorImpl(private val repository: PlayerRepository) : PlayerInteractor {
    override fun preparePlayer(url: String) {
        repository.preparePlayer(url)
    }

    override fun startPlayer() {
        repository.startPlayer()
    }

    override fun pausePlayer() {
        repository.pausePlayer()
    }

    override fun stopPlayer() {
        repository.stopPlayer()
    }

    override fun currentPosition(): Int {
        return repository.currentPosition()
    }

    override fun setOnPreparedListener(preparedListener: PlayerInteractor.PreparedListener) {
        repository.setOnPreparedListener(preparedListener)
    }

    override fun setOnCompletionListener(completionListener: PlayerInteractor.CompletionListener) {
        repository.setOnCompletionListener(completionListener)
    }


}