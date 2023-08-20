package ru.kryu.playlistmaker.sharing.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.sharing.data.ActionSend
import ru.kryu.playlistmaker.sharing.data.ActionSendTo
import ru.kryu.playlistmaker.sharing.data.ActionView
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendToImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionViewImpl

val sharingDataModule = module {
    single<ActionSend> {
        ActionSendImpl(context = get())
    }

    single<ActionSendTo> {
        ActionSendToImpl(context = get())
    }

    single<ActionView> {
        ActionViewImpl(context = get())
    }
}