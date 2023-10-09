package ru.kryu.playlistmaker.favourite.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase

val favouriteDataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}