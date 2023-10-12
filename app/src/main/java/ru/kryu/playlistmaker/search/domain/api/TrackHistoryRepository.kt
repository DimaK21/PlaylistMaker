package ru.kryu.playlistmaker.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackHistoryRepository {
    fun getTrackHistory(): Flow<List<Track>>
    fun saveTrackHistory()
    fun clearTrackHistory()
    fun addTrack(track: Track)
}