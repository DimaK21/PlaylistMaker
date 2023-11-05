package ru.kryu.playlistmaker.player.domain.impl

import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistInteractor
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository

class TrackInPlaylistInteractorImpl(private val trackInPlaylistRepository: TrackInPlaylistRepository) :
    TrackInPlaylistInteractor {
    override suspend fun isTrackInDb(idTrack: Long): Boolean {
        return trackInPlaylistRepository.isTrackInDb(idTrack)
    }

    override suspend fun addTrackInPlaylist(playlistId: Long, trackId: Long): Boolean {
        return trackInPlaylistRepository.addTrackInPlaylist(playlistId, trackId)
    }

}