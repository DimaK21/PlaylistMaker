package ru.kryu.playlistmaker.search.domain.api

import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackSearchRepository {
    fun searchTracks(expression: String): List<Track>?
}