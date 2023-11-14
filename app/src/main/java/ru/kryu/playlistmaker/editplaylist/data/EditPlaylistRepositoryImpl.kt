package ru.kryu.playlistmaker.editplaylist.data

import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistEntityMapper
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistRepository
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

class EditPlaylistRepositoryImpl(private val database: AppDatabase) : EditPlaylistRepository {
    override suspend fun updatePlaylist(playlist: Playlist) {
        database.editPlaylistDao().updatePlaylist(PlaylistEntityMapper.map(playlist))
    }
}