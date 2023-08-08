package ru.kryu.playlistmaker.creator

import android.content.Context
import ru.kryu.playlistmaker.player.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackHistoryRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.player.data.player.AndroidMediaPlayer
import ru.kryu.playlistmaker.search.data.storage.SharedPrefsHistory
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.player.domain.impl.PlayerInteractorImpl
import ru.kryu.playlistmaker.search.domain.impl.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.search.domain.impl.TrackSearchInteractorImpl

object Creator {

    private fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl(AndroidMediaPlayer())
    }

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository())
    }

    private fun getTrackSearchRepository(context: Context): TrackSearchRepository {
        return TrackSearchRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideTrackSearchInteractor(context: Context): TrackSearchInteractor {
        return TrackSearchInteractorImpl(getTrackSearchRepository(context))
    }

    private fun getTrackHistoryRepository(context: Context): TrackHistoryRepository {
        return TrackHistoryRepositoryImpl(SharedPrefsHistory(context))
    }

    fun provideTrackHistoryInteractor(context: Context): TrackHistoryInteractor {
        return TrackHistoryInteractorImpl(getTrackHistoryRepository(context))
    }
}