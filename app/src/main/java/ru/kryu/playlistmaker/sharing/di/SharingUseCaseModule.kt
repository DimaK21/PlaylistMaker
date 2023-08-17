package ru.kryu.playlistmaker.sharing.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendToUseCaseImpl
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendUseCaseImpl
import ru.kryu.playlistmaker.sharing.domain.impl.ActionViewUseCaseImpl

val sharingUseCaseModule = module {
    single {
        ActionSendUseCaseImpl(actionSendRepository = get())
    }
    single {
        ActionSendToUseCaseImpl(actionSendToRepository = get())
    }
    single {
        ActionViewUseCaseImpl(actionViewRepository = get())
    }
}