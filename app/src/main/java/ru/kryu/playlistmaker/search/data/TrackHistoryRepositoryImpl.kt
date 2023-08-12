package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.storage.mapper.MapperTrackForStorage
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackHistoryRepositoryImpl(private val historyStorage: HistoryStorage) :
    TrackHistoryRepository {
    override fun getTrackHistory(): List<Track> {
        return historyStorage.getTrackHistory()
            .map { MapperTrackForStorage().mapTrackForStorageToDomain(it) }
    }

    override fun saveTrackHistory(list: List<Track>) {
        historyStorage.saveTrackHistory(list.map {
            MapperTrackForStorage().mapTrackToTrackForStorage(it)
        })
    }

    override fun clearTrackHistory() {
        historyStorage.clearTrackHistory()
    }
}