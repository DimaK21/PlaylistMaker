package ru.kryu.playlistmaker.sharing.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.sharing.data.ActionSend
import ru.kryu.playlistmaker.sharing.data.ActionSendTo
import ru.kryu.playlistmaker.sharing.data.ActionView
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionSendToImpl
import ru.kryu.playlistmaker.sharing.data.actions_impl.ActionViewImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharingDataModule {

    @Provides
    @Singleton
    fun provideActionSend(@ApplicationContext context: Context): ActionSend =
        ActionSendImpl(context = context)

    @Provides
    @Singleton
    fun provideActionSendTo(@ApplicationContext context: Context): ActionSendTo =
        ActionSendToImpl(context = context)

    @Provides
    @Singleton
    fun provideActionView(@ApplicationContext context: Context): ActionView =
        ActionViewImpl(context = context)
}
