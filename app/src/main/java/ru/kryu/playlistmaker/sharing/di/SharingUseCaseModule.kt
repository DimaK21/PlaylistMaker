package ru.kryu.playlistmaker.sharing.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendToUseCase
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendUseCase
import ru.kryu.playlistmaker.sharing.domain.impl.ActionViewUseCase

val sharingUseCaseModule = module {
    factory {
        ActionSendUseCase(actionSendRepository = get())
    }
    factory {
        ActionSendToUseCase(actionSendToRepository = get())
    }
    factory {
        ActionViewUseCase(actionViewRepository = get())
    }
}