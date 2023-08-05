package ru.kryu.playlistmaker.search.domain.impl

import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository

class TrackHistoryInteractorImpl(private val repository: TrackHistoryRepository):
    TrackHistoryInteractor {

    override fun getTrackHistory(): List<Track> {
        return repository.getTrackHistory()
    }

    override fun saveTrackHistory(list: List<Track>) {
        repository.saveTrackHistory(list)
    }

    override fun clearTrackHistory() {
        repository.clearTrackHistory()
    }
}