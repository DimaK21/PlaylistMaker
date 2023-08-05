package ru.kryu.playlistmaker.player.domain.api

import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor

interface PlayerRepository {
    fun preparePlayer(url: String)
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun currentPosition(): Int
    fun setOnPreparedListener(preparedListener: PlayerInteractor.PreparedListener)
    fun setOnCompletionListener(completionListener: PlayerInteractor.CompletionListener)
}