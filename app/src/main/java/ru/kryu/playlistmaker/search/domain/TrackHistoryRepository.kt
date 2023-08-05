package ru.kryu.playlistmaker.search.domain

interface TrackHistoryRepository {
    fun getTrackHistory(): List<Track>
    fun saveTrackHistory(list: List<Track>)
    fun clearTrackHistory()
}