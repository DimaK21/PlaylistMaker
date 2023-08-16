package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.storage.models.TrackForStorage

interface HistoryStorage {
    fun getTrackHistory(): List<TrackForStorage>
    fun saveTrackHistory(list: List<TrackForStorage>)
    fun clearTrackHistory()
}