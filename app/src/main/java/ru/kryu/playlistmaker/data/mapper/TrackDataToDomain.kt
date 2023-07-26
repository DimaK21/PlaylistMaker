package ru.kryu.playlistmaker.data.mapper

import ru.kryu.playlistmaker.data.dto.TrackDto
import ru.kryu.playlistmaker.domain.models.Track

class TrackDataToDomain {
    fun trackDataToDomain(trackData: TrackDto): Track {
        return Track(
            trackId = trackData.trackId,
            trackName = trackData.trackName,
            artistName = trackData.artistName,
            trackTimeMillis = trackData.trackTimeMillis,
            artworkUrl100 = trackData.artworkUrl100,
            collectionName = trackData.collectionName,
            releaseDate = trackData.releaseDate,
            primaryGenreName = trackData.primaryGenreName,
            country = trackData.country,
            previewUrl = trackData.previewUrl,
        )
    }
}