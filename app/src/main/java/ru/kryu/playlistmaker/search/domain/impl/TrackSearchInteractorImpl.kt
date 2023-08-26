package ru.kryu.playlistmaker.search.domain.impl

import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.model.Resource
import java.util.concurrent.ExecutorService

class TrackSearchInteractorImpl(
    private val repository: TrackSearchRepository,
    private val executor: ExecutorService,
) : TrackSearchInteractor {

    override fun searchTracks(
        expression: String,
        consumer: TrackSearchInteractor.TrackSearchConsumer
    ) {
        executor.execute {
            when (val resource = repository.searchTracks(expression)) {
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }

                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
            }
        }
    }
}