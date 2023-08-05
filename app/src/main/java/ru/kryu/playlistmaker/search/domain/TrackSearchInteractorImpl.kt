package ru.kryu.playlistmaker.search.domain

import ru.kryu.playlistmaker.search.domain.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.TrackSearchRepository
import java.util.concurrent.Executors

class TrackSearchInteractorImpl(private val repository: TrackSearchRepository):
    TrackSearchInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(
        expression: String,
        consumer: TrackSearchInteractor.TrackSearchConsumer
    ) {
        executor.execute {
            val trackList = repository.searchTracks(expression)
            consumer.consume(trackList)
        }
    }
}