package ru.kryu.playlistmaker.search.data.storage.mapper

import ru.kryu.playlistmaker.search.data.storage.models.TrackForStorage
import ru.kryu.playlistmaker.search.domain.Track

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