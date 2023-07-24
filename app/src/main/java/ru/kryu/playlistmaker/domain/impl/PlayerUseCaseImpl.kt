package ru.kryu.playlistmaker.domain.impl

import ru.kryu.playlistmaker.domain.api.PlayerRepository
import ru.kryu.playlistmaker.domain.api.PlayerUseCase

class PlayerUseCaseImpl(private val repository: PlayerRepository) : PlayerUseCase {
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

    override fun setOnPreparedListener(preparedListener: PlayerUseCase.PreparedListener) {
        repository.setOnPreparedListener(preparedListener)
    }

    override fun setOnCompletionListener(completionListener: PlayerUseCase.CompletionListener) {
        repository.setOnCompletionListener(completionListener)
    }


}