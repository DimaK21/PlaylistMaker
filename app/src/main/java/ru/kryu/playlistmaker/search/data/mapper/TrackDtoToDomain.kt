package ru.kryu.playlistmaker.search.data.mapper

import ru.kryu.playlistmaker.search.data.dto.TrackDto
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackDtoToDomain {
    fun map(trackData: TrackDto): Track {
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