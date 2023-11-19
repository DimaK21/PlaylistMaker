package ru.kryu.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kryu.playlistmaker.createplaylist.di.createPlaylistModule
import ru.kryu.playlistmaker.editplaylist.di.editPlaylistModule
import ru.kryu.playlistmaker.favourite.di.favouriteModule
import ru.kryu.playlistmaker.player.di.playerModule
import ru.kryu.playlistmaker.playlistmain.di.playlistMainModule
import ru.kryu.playlistmaker.playlists.di.playlistsModule
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
                favouriteModule,
                settingsModule,
                sharingModule,
                searchModule,
                playlistsModule,
                createPlaylistModule,
                playlistMainModule,
                editPlaylistModule,
            )
        }

        PermissionRequester.initialize(applicationContext)

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