package ru.kryu.playlistmaker.playlistmain.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistWithTracksMapper
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain

class PlaylistMainRepositoryImpl(private val database: AppDatabase) : PlaylistMainRepository {
    override fun getPlaylistMain(playlistId: Long): Flow<PlaylistMain> {
        return database.playlistMainDao()
            .getPlaylistWithTracks(playlistId)
            .map { playlistWithTracks ->
                PlaylistWithTracksMapper.map(playlistWithTracks)
            }
    }
}