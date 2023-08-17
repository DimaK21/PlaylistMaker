package ru.kryu.playlistmaker.player.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.impl.PlayerInteractorImpl

val playerInteractorModule = module {

    single<PlayerInteractor> {
        PlayerInteractorImpl(repository = get())
    }
}