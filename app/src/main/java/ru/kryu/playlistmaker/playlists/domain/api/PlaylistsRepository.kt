package ru.kryu.playlistmaker.playlists.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

interface PlaylistsRepository {
    fun getPlaylists(): Flow<List<Playlist>>
}