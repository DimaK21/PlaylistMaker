package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.storage.mapper.MapperTrackForStorage
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackHistoryRepositoryImpl(private val historyStorage: HistoryStorage) :
    TrackHistoryRepository {

    private val trackHistory = getTrackHistoryFromStorage().toMutableList()

    private fun getTrackHistoryFromStorage(): List<Track> {
        return historyStorage.getTrackHistory()
            .map { MapperTrackForStorage().mapTrackForStorageToDomain(it) }
    }

    override fun getTrackHistory(): List<Track> {
        return trackHistory
    }

    override fun saveTrackHistory() {
        historyStorage.saveTrackHistory(trackHistory.map {
            MapperTrackForStorage().mapTrackToTrackForStorage(it)
        })
    }

    override fun clearTrackHistory() {
        historyStorage.clearTrackHistory()
        trackHistory.clear()
    }

    override fun addTrack(track: Track) {
        trackHistory.removeIf { it.trackId == track.trackId }
        trackHistory.add(0, track)
        if (trackHistory.size > TRACK_HISTORY_SIZE) trackHistory.removeLast()
    }

    companion object {
        private const val TRACK_HISTORY_SIZE = 10
    }
}