package ru.kryu.playlistmaker.settings.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.settings.data.DarkThemeRepositoryImpl
import ru.kryu.playlistmaker.settings.data.DarkThemeStorage
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsRepositoryModule {
    @Provides
    fun provideDarkThemeRepository(
        darkThemeStorage: DarkThemeStorage,
    ): DarkThemeRepository =
        DarkThemeRepositoryImpl(darkThemeStorage = darkThemeStorage)

}
