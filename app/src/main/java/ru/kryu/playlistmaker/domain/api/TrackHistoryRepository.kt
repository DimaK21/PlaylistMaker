package ru.kryu.playlistmaker.domain.api

import ru.kryu.playlistmaker.domain.models.Track

interface TrackHistoryRepository {
    fun getTrackHistory(): List<Track>
    fun saveTrackHistory(list: List<Track>)
    fun clearTrackHistory()
}