package ru.kryu.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kryu.playlistmaker.creator.Creator
import ru.kryu.playlistmaker.player.di.audioPlayerViewModelModule
import ru.kryu.playlistmaker.player.di.playerDataModule
import ru.kryu.playlistmaker.player.di.playerInteractorModule
import ru.kryu.playlistmaker.player.di.playerModule
import ru.kryu.playlistmaker.player.di.playerRepositoryModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                playerModule,

            )
        }

        val darkThemeInteractor = Creator.provideDarkThemeInteractor(this)
        switchTheme(darkThemeInteractor.getDarkTheme())
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}