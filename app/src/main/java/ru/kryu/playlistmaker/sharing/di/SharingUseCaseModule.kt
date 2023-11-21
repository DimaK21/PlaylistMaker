package ru.kryu.playlistmaker.sharing.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendToUseCase
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendUseCase
import ru.kryu.playlistmaker.sharing.domain.impl.ActionViewUseCase

@Module
@InstallIn(ViewModelComponent::class)
class SharingUseCaseModule {
    @Provides
    fun provideActionSendUseCase(actionSendRepository: ActionSendRepository): ActionSendUseCase =
        ActionSendUseCase(actionSendRepository = actionSendRepository)

    @Provides
    fun provideActionSendToUseCase(actionSendToRepository: ActionSendToRepository): ActionSendToUseCase =
        ActionSendToUseCase(actionSendToRepository = actionSendToRepository)

    @Provides
    fun provideActionViewUseCase(actionViewRepository: ActionViewRepository): ActionViewUseCase =
        ActionViewUseCase(actionViewRepository = actionViewRepository)
}