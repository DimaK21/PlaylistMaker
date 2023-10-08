package ru.kryu.playlistmaker.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackSearchInteractor {
    fun searchTracks(expression: String): Flow<Pair<List<Track>?, String?>>
}