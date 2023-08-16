package ru.kryu.playlistmaker.player.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.player.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository

val playerRepositoryModule = module {

    single<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }
}