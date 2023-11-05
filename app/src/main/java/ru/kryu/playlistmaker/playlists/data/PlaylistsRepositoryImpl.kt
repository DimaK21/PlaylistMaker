package ru.kryu.playlistmaker.playlists.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistEntityMapper
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

class PlaylistsRepositoryImpl(private val database: AppDatabase) : PlaylistsRepository {
    override fun getPlaylists(): Flow<List<Playlist>> = flow {
        val list: List<Playlist> = database.playlistDao().getPlaylists().map { playlistEntity ->
            PlaylistEntityMapper.map(playlistEntity).apply {
                countTracks = database.playlistDao().getCountTracksInPlaylist(playlistId!!)
            }
        }
        emit(list)
    }
}