package ru.kryu.playlistmaker.creator

import android.content.Context
import ru.kryu.playlistmaker.player.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackHistoryRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.player.data.player.AndroidMediaPlayer
import ru.kryu.playlistmaker.search.data.storage.SharedPrefsHistory
import ru.kryu.playlistmaker.player.domain.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.PlayerRepository
import ru.kryu.playlistmaker.search.domain.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.TrackSearchRepository
import ru.kryu.playlistmaker.player.domain.PlayerInteractorImpl
import ru.kryu.playlistmaker.search.domain.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.search.domain.TrackSearchInteractorImpl

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