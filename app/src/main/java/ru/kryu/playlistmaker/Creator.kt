package ru.kryu.playlistmaker

import android.content.Context
import ru.kryu.playlistmaker.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.data.TrackHistoryRepositoryImpl
import ru.kryu.playlistmaker.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.data.player.AndroidMediaPlayer
import ru.kryu.playlistmaker.data.storage.SharedPrefsHistory
import ru.kryu.playlistmaker.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.domain.api.PlayerRepository
import ru.kryu.playlistmaker.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.domain.impl.PlayerInteractorImpl
import ru.kryu.playlistmaker.domain.impl.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.domain.impl.TrackSearchInteractorImpl
import ru.kryu.playlistmaker.ui.App

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

    private fun getTrackHistoryRepository(context: Context): TrackHistoryRepository {
        return TrackHistoryRepositoryImpl(SharedPrefsHistory(context))
    }

    fun provideTrackHistoryInteractor(context: Context): TrackHistoryInteractor {
        return TrackHistoryInteractorImpl(getTrackHistoryRepository(context))
    }
}