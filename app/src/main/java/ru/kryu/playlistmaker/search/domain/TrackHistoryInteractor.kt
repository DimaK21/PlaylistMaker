package ru.kryu.playlistmaker.search.domain

interface TrackHistoryInteractor {
    fun getTrackHistory(): List<Track>
    fun saveTrackHistory(list: List<Track>)
    fun clearTrackHistory()
}