package ru.kryu.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kryu.playlistmaker.creator.Creator
import ru.kryu.playlistmaker.media.di.mediaModule
import ru.kryu.playlistmaker.player.di.playerModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                playerModule,
                mediaModule,
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