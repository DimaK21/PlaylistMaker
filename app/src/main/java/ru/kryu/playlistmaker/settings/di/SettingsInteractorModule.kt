package ru.kryu.playlistmaker.settings.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeInteractor
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeRepository
import ru.kryu.playlistmaker.settings.domain.impl.DarkThemeInteractorImpl
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
class SettingsInteractorModule {
    @ProvideDarkThemeInteractor
    @Provides
    fun provideDarkThemeInteractor(darkThemeRepository: DarkThemeRepository): DarkThemeInteractor =
        DarkThemeInteractorImpl(darkThemeRepository = darkThemeRepository)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractSettingsInteractorModule {
    @BindDarkThemeInteractor
    @Binds
    abstract fun bindDarkThemeInteractor(darkThemeInteractorImpl: DarkThemeInteractorImpl): DarkThemeInteractor
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProvideDarkThemeInteractor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindDarkThemeInteractor
