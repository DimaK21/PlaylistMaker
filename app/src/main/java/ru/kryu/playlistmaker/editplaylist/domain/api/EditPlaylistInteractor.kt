package ru.kryu.playlistmaker.editplaylist.domain.api

import ru.kryu.playlistmaker.playlists.domain.model.Playlist

interface EditPlaylistInteractor {

    suspend fun updatePlaylist(playlist: Playlist)
}