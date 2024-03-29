package ru.kryu.playlistmaker.search.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.search.data.storage.mapper.MapperTrackForStorage
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackHistoryRepositoryImpl(
    private val historyStorage: HistoryStorage,
    private val trackHistory: MutableList<Track>,
    private val database: AppDatabase,
) : TrackHistoryRepository {

    init {
        trackHistory.addAll(historyStorage.getTrackHistory()
            .map { MapperTrackForStorage().mapTrackForStorageToDomain(it) })
    }

    override fun getTrackHistory(): Flow<List<Track>> = flow {
        val favouritesTracks = database.trackFavouriteDao().getIdTracks()
        trackHistory.forEach { track ->
            if (favouritesTracks.contains(track.trackId)) {
                track.isFavorite = true
            }
        }
        emit(trackHistory)
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