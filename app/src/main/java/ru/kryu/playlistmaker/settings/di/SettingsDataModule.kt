package ru.kryu.playlistmaker.settings.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kryu.playlistmaker.settings.data.DarkThemeStorage
import ru.kryu.playlistmaker.settings.data.SharedPrefsDarkTheme

const val USER_PREFERENCES = "user_preferences"

val settingsDataModule = module {
    single<DarkThemeStorage> {
        SharedPrefsDarkTheme(sharedPrefs = get())
    }
    single {
        androidContext().getSharedPreferences(
            USER_PREFERENCES,
            Application.MODE_PRIVATE
        )
    }
}