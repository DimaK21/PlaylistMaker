package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.storage.mapper.MapperTrackForStorage
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackHistoryRepositoryImpl(private val historyStorage: HistoryStorage) :
    TrackHistoryRepository {

    private val trackHistory = getTrackHistoryFromStorage()

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
        (trackHistory as MutableList<Track>).clear()
    }

    override fun addTrack(track: Track) {
        with(trackHistory as MutableList<Track>) {
            this.removeIf { it.trackId == track.trackId }
            this.add(0, track)
            if (this.size > TRACK_HISTORY_SIZE) this.removeLast()
        }
    }

    companion object {
        private const val TRACK_HISTORY_SIZE = 10
    }
}