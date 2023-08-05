package ru.kryu.playlistmaker.search.domain

interface TrackSearchRepository {
    fun searchTracks(expression: String): List<Track>?
}