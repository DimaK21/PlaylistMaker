package ru.kryu.playlistmaker.presentation.search

import android.content.Context
import ru.kryu.playlistmaker.Creator
import ru.kryu.playlistmaker.presentation.mapper.TrackForUiToDomain
import ru.kryu.playlistmaker.presentation.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.presentation.models.TrackForUi

class SearchHistory(private val context: Context) {

    private val trackHistoryInteractor = Creator.provideTrackHistoryInteractor(context)

    private val arrayListTrackHistory: ArrayList<TrackForUi> = getTrackHistory()
    val listTrackHistory: List<TrackForUi> = arrayListTrackHistory

    fun addTrack(track: TrackForUi) {
        arrayListTrackHistory.removeIf { it.trackId == track.trackId }
        arrayListTrackHistory.add(0, track)
        if (arrayListTrackHistory.size > TRACK_HISTORY_SIZE) arrayListTrackHistory.removeLast()
    }

    private fun getTrackHistory(): ArrayList<TrackForUi> {
        return trackHistoryInteractor.getTrackHistory()
            .map { TrackToTrackForUi().trackToTrackForUi(it) } as ArrayList<TrackForUi>
    }

    fun saveTrackHistory() {
        trackHistoryInteractor.saveTrackHistory(listTrackHistory.map {
            TrackForUiToDomain().trackForUiToDomain(
                it
            )
        })
    }

    fun clearTrackHistory() {
        arrayListTrackHistory.clear()
        trackHistoryInteractor.clearTrackHistory()
    }

    companion object {
        const val TRACK_HISTORY_SIZE = 10
    }
}