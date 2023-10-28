package ru.kryu.playlistmaker.createplaylist.domain.api

import ru.kryu.playlistmaker.playlists.domain.model.Playlist

interface CreatePlaylistRepository {
    suspend fun createPlaylist(playlist: Playlist)
    suspend fun saveImageToPrivateStorage(path: String, imageId: String)
}