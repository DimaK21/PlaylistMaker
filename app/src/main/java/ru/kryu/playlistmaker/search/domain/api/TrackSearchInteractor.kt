package ru.kryu.playlistmaker.search.domain.api

import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String, consumer: TrackSearchConsumer)

    fun interface TrackSearchConsumer{
        fun consume(foundTracks: List<Track>?)
    }
}