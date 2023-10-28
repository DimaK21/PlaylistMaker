package ru.kryu.playlistmaker.createplaylist.domain.api

import ru.kryu.playlistmaker.playlist.domain.model.Playlist

interface CreatePlaylistRepository {
    suspend fun createPlaylist(playlist: Playlist)
    suspend fun saveImageToPrivateStorage(path: String?)
}