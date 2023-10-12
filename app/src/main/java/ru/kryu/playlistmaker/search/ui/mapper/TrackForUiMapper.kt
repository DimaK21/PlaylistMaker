package ru.kryu.playlistmaker.search.ui.mapper

import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

object TrackForUiMapper {
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
            isFavorite = trackForUI.isFavorite
        )
    }

    fun map(track: Track): TrackForUi {
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
            artworkUrl512 = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"),
            isFavorite = track.isFavorite
        )
    }
}