package ru.kryu.playlistmaker.settings.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.settings.data.DarkThemeStorage
import ru.kryu.playlistmaker.settings.data.SharedPrefsDarkTheme
import javax.inject.Qualifier
import javax.inject.Singleton

const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
class SettingsDataModule {
    @SharedPreferencesSettingsDataModule
    @Provides
    fun provideDarkThemeStorage(
        sharedPrefs: SharedPreferences,
    ): DarkThemeStorage =
        SharedPrefsDarkTheme(sharedPrefs = sharedPrefs)

    @Provides
    fun provideDarkThemeStorage2(
        sharedPrefsDarkTheme: SharedPrefsDarkTheme,
    ): DarkThemeStorage =
        sharedPrefsDarkTheme

    @SharedPreferencesSettingsDataModule
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            USER_PREFERENCES,
            Application.MODE_PRIVATE
        )
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferencesSettingsDataModule

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferencesSearchDataModule