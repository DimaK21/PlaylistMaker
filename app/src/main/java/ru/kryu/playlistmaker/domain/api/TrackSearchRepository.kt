package ru.kryu.playlistmaker.domain.api

import ru.kryu.playlistmaker.domain.models.Track

interface TrackSearchRepository {
    fun searchTracks(expression: String): List<Track>?
}