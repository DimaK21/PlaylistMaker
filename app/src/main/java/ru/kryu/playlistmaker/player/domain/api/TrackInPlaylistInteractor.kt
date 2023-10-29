package ru.kryu.playlistmaker.player.domain.api

interface TrackInPlaylistInteractor {
    suspend fun isTrackInDb(idTrack: Long): Boolean
    suspend fun addTrackInPlaylist(playlistId: Long, trackId: Long): Boolean
}