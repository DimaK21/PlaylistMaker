package ru.kryu.playlistmaker

import ru.kryu.playlistmaker.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.data.player.AndroidMediaPlayer
import ru.kryu.playlistmaker.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.domain.api.PlayerRepository
import ru.kryu.playlistmaker.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.domain.impl.PlayerInteractorImpl
import ru.kryu.playlistmaker.domain.impl.TrackSearchInteractorImpl

object Creator {

    private fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl(AndroidMediaPlayer())
    }

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository())
    }

    private fun getTrackSearchRepository(): TrackSearchRepository {
        return TrackSearchRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTrackSearchInteractor(): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTrackSearchRepository())
    }
}