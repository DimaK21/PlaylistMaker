package ru.kryu.playlistmaker.domain.api

import ru.kryu.playlistmaker.domain.models.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String, consumer:TrackSearchConsumer)

    fun interface TrackSearchConsumer{
        fun consume(foundTracks: List<Track>?)
    }
}