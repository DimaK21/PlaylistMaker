package ru.kryu.playlistmaker.data.storage.mapper

import ru.kryu.playlistmaker.data.storage.models.TrackForStorage
import ru.kryu.playlistmaker.domain.models.Track

class TrackForStorageToDomain {
    fun trackForStorageToDomain(trackForStorage: TrackForStorage): Track {
        return Track(
            trackId = trackForStorage.trackId,
            trackName = trackForStorage.trackName,
            artistName = trackForStorage.artistName,
            trackTimeMillis = trackForStorage.trackTimeMillis,
            artworkUrl100 = trackForStorage.artworkUrl100,
            collectionName = trackForStorage.collectionName,
            releaseDate = trackForStorage.releaseDate,
            primaryGenreName = trackForStorage.primaryGenreName,
            country = trackForStorage.country,
            previewUrl = trackForStorage.previewUrl,
        )
    }
}