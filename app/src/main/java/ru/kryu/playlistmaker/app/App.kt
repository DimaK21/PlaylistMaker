package ru.kryu.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import ru.kryu.playlistmaker.creator.Creator

class App : Application() {

    override fun onCreate() {
        super.onCreate()

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