package ru.kryu.playlistmaker.sharing.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kryu.playlistmaker.sharing.data.ActionSend
import ru.kryu.playlistmaker.sharing.data.ActionSendTo
import ru.kryu.playlistmaker.sharing.data.ActionView
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendToImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionViewImpl

val sharingDataModule = module {
    single<ActionSend> {
        ActionSendImpl(context = androidContext())
    }

    single<ActionSendTo> {
        ActionSendToImpl(context = androidContext())
    }

    single<ActionView> {
        ActionViewImpl(context = androidContext())
    }
}