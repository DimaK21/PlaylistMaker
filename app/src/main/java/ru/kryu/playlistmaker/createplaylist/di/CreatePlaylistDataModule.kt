package ru.kryu.playlistmaker.createplaylist.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kryu.playlistmaker.createplaylist.data.ImageStorage
import ru.kryu.playlistmaker.createplaylist.data.storage.ExternalPrivateStorage

val createPlaylistDataModule = module {
    single<ImageStorage> {
        ExternalPrivateStorage(context = androidContext())
    }
}