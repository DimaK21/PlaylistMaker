package ru.kryu.playlistmaker.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.model.Track
import javax.inject.Inject

class TrackHistoryInteractorImpl @Inject constructor(private val repository: TrackHistoryRepository) :
    TrackHistoryInteractor {

    override fun getTrackHistory(): Flow<List<Track>> {
        return repository.getTrackHistory()
    }

    override fun saveTrackHistory() {
        repository.saveTrackHistory()
    }

    override fun clearTrackHistory() {
        repository.clearTrackHistory()
    }

    override fun addTrack(track: Track) {
        repository.addTrack(track)
    }
}