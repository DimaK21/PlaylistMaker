package ru.kryu.playlistmaker.presentation.mapper

import ru.kryu.playlistmaker.domain.models.Track
import ru.kryu.playlistmaker.presentation.models.TrackForUi

class TrackForUiToDomain {
    fun trackForUiToDomain(trackForUI: TrackForUi): Track {
        return Track(
            trackId = trackForUI.trackId,
            trackName = trackForUI.trackName,
            artistName = trackForUI.artistName,
            trackTimeMillis = trackForUI.trackTimeMillis,
            artworkUrl100 = trackForUI.artworkUrl100,
            collectionName = trackForUI.collectionName,
            releaseDate = trackForUI.releaseDate,
            primaryGenreName = trackForUI.primaryGenreName,
            country = trackForUI.country,
            previewUrl = trackForUI.previewUrl,
        )
    }
}