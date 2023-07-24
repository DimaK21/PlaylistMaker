package ru.kryu.playlistmaker

import ru.kryu.playlistmaker.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.data.player.AndroidMediaPlayer
import ru.kryu.playlistmaker.domain.api.PlayerRepository
import ru.kryu.playlistmaker.domain.api.PlayerUseCase
import ru.kryu.playlistmaker.domain.impl.PlayerUseCaseImpl

object Creator {

    private fun getPlayerRepository(): PlayerRepository{
        return PlayerRepositoryImpl(AndroidMediaPlayer())
    }

    fun providePlayerUseCase(): PlayerUseCase{
        return PlayerUseCaseImpl(getPlayerRepository())
    }
}