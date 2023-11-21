package ru.kryu.playlistmaker.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.model.Resource
import ru.kryu.playlistmaker.search.domain.model.Track
import javax.inject.Inject

class TrackSearchInteractorImpl @Inject constructor(
    private val repository: TrackSearchRepository,
) : TrackSearchInteractor {
    override fun searchTracks(expression: String): Flow<Pair<List<Track>?, String?>> {
        return repository.searchTracks(expression).map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Pair(resource.data, null)
                }

                is Resource.Error -> {
                    Pair(null, resource.message)
                }
            }
        }
    }
}