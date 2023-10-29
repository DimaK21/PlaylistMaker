package ru.kryu.playlistmaker.player.domain.api

import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackInPlaylistRepository {

    suspend fun isTrackInDb(idTrack: Long): Boolean
    suspend fun addTrackInPlaylist(playlist: Playlist, track: Track): Boolean
}