package ru.kryu.playlistmaker.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.domain.model.Resource

interface TrackSearchRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
}