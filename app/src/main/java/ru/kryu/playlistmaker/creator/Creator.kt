package ru.kryu.playlistmaker.creator

import android.content.Context
import ru.kryu.playlistmaker.player.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.player.data.player.AndroidMediaPlayer
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository
import ru.kryu.playlistmaker.player.domain.impl.PlayerInteractorImpl
import ru.kryu.playlistmaker.search.data.TrackHistoryRepositoryImpl
import ru.kryu.playlistmaker.search.data.TrackSearchRepositoryImpl
import ru.kryu.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.search.data.storage.SharedPrefsHistory
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.impl.TrackHistoryInteractorImpl
import ru.kryu.playlistmaker.search.domain.impl.TrackSearchInteractorImpl
import ru.kryu.playlistmaker.sharing.data.ActionSendRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.ActionSendToRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.ActionViewRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendToImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionViewImpl
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToUseCase
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendUseCase
import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionViewUseCase
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendToUseCaseImpl
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendUseCaseImpl
import ru.kryu.playlistmaker.sharing.domain.impl.ActionViewUseCaseImpl

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

    private fun getActionSendRepository(context: Context): ActionSendRepository {
        return ActionSendRepositoryImpl(ActionSendImpl(context))
    }

    fun provideActionSendUseCase(context: Context): ActionSendUseCase {
        return ActionSendUseCaseImpl(getActionSendRepository(context))
    }

    private fun getActionSendToRepository(context: Context): ActionSendToRepository {
        return ActionSendToRepositoryImpl(ActionSendToImpl(context))
    }

    fun provideActionSendToUseCase(context: Context): ActionSendToUseCase {
        return ActionSendToUseCaseImpl(getActionSendToRepository(context))
    }

    private fun getActionViewRepository(context: Context): ActionViewRepository {
        return ActionViewRepositoryImpl(ActionViewImpl(context))
    }

    fun provideActionViewUseCase(context: Context): ActionViewUseCase {
        return ActionViewUseCaseImpl(getActionViewRepository(context))
    }

}