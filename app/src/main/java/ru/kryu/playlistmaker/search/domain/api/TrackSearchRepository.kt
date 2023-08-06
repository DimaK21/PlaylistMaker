package ru.kryu.playlistmaker.search.domain.api

import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.util.Resource

interface TrackSearchRepository {
    fun searchTracks(expression: String): Resource<List<Track>>
}