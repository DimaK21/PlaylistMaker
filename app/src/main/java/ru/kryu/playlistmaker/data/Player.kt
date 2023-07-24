package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.domain.api.PlayerUseCase

interface Player {

    fun preparePlayer(dto: Any)
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun currentPosition(): Int
    fun setOnPreparedListener(preparedListener: PlayerUseCase.PreparedListener)
    fun setOnCompletionListener(completionListener: PlayerUseCase.CompletionListener)
}