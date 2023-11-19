package ru.kryu.playlistmaker.playlistmain.di

import org.koin.dsl.module
import ru.kryu.playlistmaker.playlistmain.data.PlaylistMainRepositoryImpl
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository

val playlistMainRepositoryModule = module {
    single<PlaylistMainRepository> {
        PlaylistMainRepositoryImpl(database = get())
    }
}