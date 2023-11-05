package ru.kryu.playlistmaker.playlists.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

interface PlaylistsInteractor {
    fun getPlaylists(): Flow<List<Playlist>>
}