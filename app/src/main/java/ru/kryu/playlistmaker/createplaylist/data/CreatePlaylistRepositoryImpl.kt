package ru.kryu.playlistmaker.createplaylist.data

import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistEntityMapper
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import javax.inject.Inject

class CreatePlaylistRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    private val imageStorage: ImageStorage
) : CreatePlaylistRepository {
    override suspend fun createPlaylist(playlist: Playlist) {
        database.playlistDao().addPlaylist(playlist = PlaylistEntityMapper.map(playlist))
    }

    override suspend fun saveImageToPrivateStorage(path: String, imageId: String) {
        imageStorage.saveImage(path, imageId)
    }
}