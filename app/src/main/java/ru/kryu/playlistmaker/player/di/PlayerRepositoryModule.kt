package ru.kryu.playlistmaker.player.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.player.data.Player
import ru.kryu.playlistmaker.player.data.PlayerRepositoryImpl
import ru.kryu.playlistmaker.player.data.TrackInPlaylistRepositoryImpl
import ru.kryu.playlistmaker.player.domain.api.PlayerRepository
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PlayerRepositoryModule {
    @Provides
    fun providePlayerRepository(
        player: Player,
    ): PlayerRepository =
        PlayerRepositoryImpl(player = player)

    @Provides
    @Singleton
    fun provideTrackInPlaylistRepository(
        database: AppDatabase,
    ): TrackInPlaylistRepository =
        TrackInPlaylistRepositoryImpl(database = database)
}