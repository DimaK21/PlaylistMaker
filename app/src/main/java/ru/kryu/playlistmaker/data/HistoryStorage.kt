package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.data.storage.models.TrackForStorage
import ru.kryu.playlistmaker.domain.models.Track

interface HistoryStorage {
    fun getTrackHistory(): List<TrackForStorage>
    fun saveTrackHistory(list: List<TrackForStorage>)
    fun clearTrackHistory()
}