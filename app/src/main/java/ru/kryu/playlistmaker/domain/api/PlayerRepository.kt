package ru.kryu.playlistmaker.domain.api

interface PlayerRepository {
    fun preparePlayer(url: String)
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun currentPosition(): Int
    fun setOnPreparedListener(preparedListener: PlayerUseCase.PreparedListener)
    fun setOnCompletionListener(completionListener: PlayerUseCase.CompletionListener)
}