package ru.kryu.playlistmaker.sharing.di

import org.koin.dsl.module

val sharingModule = module {
    includes(
        sharingDataModule,
        sharingRepositoryModule,
        sharingUseCaseModule,
    )
}