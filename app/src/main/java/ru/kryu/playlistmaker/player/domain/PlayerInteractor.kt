package ru.kryu.playlistmaker.player.domain

interface PlayerInteractor {
    fun preparePlayer(url: String)

    fun startPlayer()

    fun pausePlayer()

    fun stopPlayer()

    fun currentPosition(): Int

    fun setOnPreparedListener(preparedListener: PreparedListener)
    fun setOnCompletionListener(completionListener: CompletionListener)

    interface PreparedListener {
        fun setOnPreparedListener()
    }

    interface CompletionListener {
        fun setOnCompletionListener()
    }
}