package ru.kryu.playlistmaker.sharing.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.sharing.data.ActionSend
import ru.kryu.playlistmaker.sharing.data.ActionSendRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.ActionSendTo
import ru.kryu.playlistmaker.sharing.data.ActionSendToRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.ActionView
import ru.kryu.playlistmaker.sharing.data.ActionViewRepositoryImpl
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharingRepositoryModule {
    @Provides
    @Singleton
    fun provideActionSendRepository(
        actionSend: ActionSend,
    ): ActionSendRepository =
        ActionSendRepositoryImpl(actionSend = actionSend)

    @Provides
    @Singleton
    fun provideActionSendToRepository(
        actionSendTo: ActionSendTo,
    ): ActionSendToRepository =
        ActionSendToRepositoryImpl(actionSendTo = actionSendTo)

    @Provides
    @Singleton
    fun provideActionViewRepository(
        actionView: ActionView,
    ): ActionViewRepository =
        ActionViewRepositoryImpl(actionView = actionView)
}
