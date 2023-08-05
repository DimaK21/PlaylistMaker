package ru.kryu.playlistmaker.player.data

import ru.kryu.playlistmaker.player.domain.PlayerInteractor

interface Player {

    fun preparePlayer(dto: Any)
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun currentPosition(): Int
    fun setOnPreparedListener(preparedListener: PlayerInteractor.PreparedListener)
    fun setOnCompletionListener(completionListener: PlayerInteractor.CompletionListener)
}