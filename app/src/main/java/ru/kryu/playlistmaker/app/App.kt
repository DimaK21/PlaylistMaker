package ru.kryu.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.markodevcic.peko.PermissionRequester
import dagger.hilt.android.HiltAndroidApp
import ru.kryu.playlistmaker.settings.di.BindDarkThemeInteractor
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeInteractor
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @BindDarkThemeInteractor
    @Inject lateinit var darkThemeInteractor: DarkThemeInteractor

    override fun onCreate() {
        super.onCreate()

        PermissionRequester.initialize(applicationContext)
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