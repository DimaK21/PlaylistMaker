package ru.kryu.playlistmaker.search.ui.mapper

import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class TrackForUiToDomain {
    fun map(trackForUI: TrackForUi): Track {
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