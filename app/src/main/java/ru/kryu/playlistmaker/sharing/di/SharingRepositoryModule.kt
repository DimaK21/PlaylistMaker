package ru.kryu.playlistmaker.sharing.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.sharing.data.ActionSendRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.ActionSendToRepositoryImpl
import ru.kryu.playlistmaker.sharing.data.ActionViewRepositoryImpl
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository

val sharingRepositoryModule = module {
    single<ActionSendRepository> {
        ActionSendRepositoryImpl(actionSend = get())
    }
    single<ActionSendToRepository> {
        ActionSendToRepositoryImpl(actionSendTo = get())
    }
    single<ActionViewRepository> {
        ActionViewRepositoryImpl(actionView = get())
    }
}