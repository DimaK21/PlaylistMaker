package ru.kryu.playlistmaker.presentation.search

import android.content.Context
import ru.kryu.playlistmaker.Creator
import ru.kryu.playlistmaker.domain.models.Track

class SearchHistory(private val context: Context) {

    private val arrayListTrackHistory: ArrayList<Track> = getTrackHistory()
    val listTrackHistory: List<Track> = arrayListTrackHistory

    private val creator = Creator
    private val trackHistoryInteractor = creator.provideTrackHistoryInteractor(context)

    fun addTrack(track: Track) {
        arrayListTrackHistory.removeIf { it.trackId == track.trackId }
        arrayListTrackHistory.add(0, track)
        if (arrayListTrackHistory.size > TRACK_HISTORY_SIZE) arrayListTrackHistory.removeLast()
    }

    private fun getTrackHistory(): ArrayList<Track> {
        return trackHistoryInteractor.getTrackHistory() as ArrayList<Track>
    }

    fun saveTrackHistory() {
        trackHistoryInteractor.saveTrackHistory(listTrackHistory)
    }

    fun clearTrackHistory() {
        arrayListTrackHistory.clear()
        trackHistoryInteractor.clearTrackHistory()
    }

    companion object {
        const val TRACK_HISTORY_SIZE = 10
    }
}