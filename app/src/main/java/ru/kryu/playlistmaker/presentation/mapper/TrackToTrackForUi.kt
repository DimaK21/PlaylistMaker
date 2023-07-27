package ru.kryu.playlistmaker.presentation.mapper

import ru.kryu.playlistmaker.domain.models.Track
import ru.kryu.playlistmaker.presentation.models.TrackForUi

class TrackToTrackForUi {
    fun trackToTrackForUi(track: Track): TrackForUi {
        return TrackForUi(
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
            artworkUrl512 = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
        )
    }
}