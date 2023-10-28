package ru.kryu.playlistmaker.createplaylist.data

import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistEntityMapper
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlist.domain.model.Playlist

class CreatePlaylistRepositoryImpl(private val database: AppDatabase) : CreatePlaylistRepository {
    override suspend fun createPlaylist(playlist: Playlist) {
        database.playlistDao().addPlaylist(playlist = PlaylistEntityMapper.map(playlist))
    }
}