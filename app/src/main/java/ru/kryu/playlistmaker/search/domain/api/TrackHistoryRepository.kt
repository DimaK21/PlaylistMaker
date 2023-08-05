package ru.kryu.playlistmaker.search.domain.api

import ru.kryu.playlistmaker.search.domain.model.Track

interface TrackHistoryRepository {
    fun getTrackHistory(): List<Track>
    fun saveTrackHistory(list: List<Track>)
    fun clearTrackHistory()
}