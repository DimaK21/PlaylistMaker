package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.domain.api.PlayerInteractor

interface Player {

    fun preparePlayer(dto: Any)
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun currentPosition(): Int
    fun setOnPreparedListener(preparedListener: PlayerInteractor.PreparedListener)
    fun setOnCompletionListener(completionListener: PlayerInteractor.CompletionListener)
}