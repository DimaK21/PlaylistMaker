package ru.kryu.playlistmaker.data.storage.mapper

import ru.kryu.playlistmaker.data.storage.models.TrackForStorage
import ru.kryu.playlistmaker.domain.models.Track

class TrackToTrackForStorage {
    fun trackToTrackForStorage(track: Track): TrackForStorage {
        return TrackForStorage(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
        )
    }
}