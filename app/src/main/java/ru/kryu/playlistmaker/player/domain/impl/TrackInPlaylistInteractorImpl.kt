package ru.kryu.playlistmaker.player.domain.impl

import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistInteractor
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackInPlaylistInteractorImpl(private val trackInPlaylistRepository: TrackInPlaylistRepository) :
    TrackInPlaylistInteractor {
    override suspend fun isTrackInDb(idTrack: Long): Boolean {
        return trackInPlaylistRepository.isTrackInDb(idTrack)
    }

    override suspend fun addTrackInPlaylist(playlist: Playlist, track: Track): Boolean {
        return trackInPlaylistRepository.addTrackInPlaylist(playlist, track)
    }

}