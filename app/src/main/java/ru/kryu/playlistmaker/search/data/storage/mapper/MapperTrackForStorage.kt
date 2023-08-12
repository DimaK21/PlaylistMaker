package ru.kryu.playlistmaker.search.data.storage.mapper

import ru.kryu.playlistmaker.search.data.storage.models.TrackForStorage
import ru.kryu.playlistmaker.search.domain.model.Track

class MapperTrackForStorage {
    fun mapTrackForStorageToDomain(trackForStorage: TrackForStorage): Track {
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

    fun mapTrackToTrackForStorage(track: Track): TrackForStorage {
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

