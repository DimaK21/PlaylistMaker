package ru.kryu.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kryu.playlistmaker.media.di.mediaModule
import ru.kryu.playlistmaker.player.di.playerModule
import ru.kryu.playlistmaker.search.di.searchModule
import ru.kryu.playlistmaker.settings.di.settingsModule
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeInteractor
import ru.kryu.playlistmaker.sharing.di.sharingModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                playerModule,
                mediaModule,
                settingsModule,
                sharingModule,
                searchModule,
            )
        }

        val darkThemeInteractor: DarkThemeInteractor by inject()
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