package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.data.storage.mapper.TrackForStorageToDomain
import ru.kryu.playlistmaker.data.storage.mapper.TrackToTrackForStorage
import ru.kryu.playlistmaker.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.domain.models.Track

class TrackHistoryRepositoryImpl(private val historyStorage: HistoryStorage) :
    TrackHistoryRepository {
    override fun getTrackHistory(): List<Track> {
        return historyStorage.getTrackHistory()
            .map { TrackForStorageToDomain().trackForStorageToDomain(it) }
    }

    override fun saveTrackHistory(list: List<Track>) {
        historyStorage.saveTrackHistory(list.map {
            TrackToTrackForStorage().trackToTrackForStorage(it)
        })
    }

    override fun clearTrackHistory() {
        historyStorage.clearTrackHistory()
    }
}