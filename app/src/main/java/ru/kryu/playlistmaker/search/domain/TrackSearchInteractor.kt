package ru.kryu.playlistmaker.search.domain

interface TrackSearchInteractor {
    fun searchTracks(expression: String, consumer: TrackSearchConsumer)

    fun interface TrackSearchConsumer{
        fun consume(foundTracks: List<Track>?)
    }
}