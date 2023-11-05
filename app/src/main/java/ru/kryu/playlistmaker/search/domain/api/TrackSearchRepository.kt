package ru.kryu.playlistmaker.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.search.domain.model.Resource
import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackSearchRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
}