package ru.kryu.playlistmaker.search.domain.impl

import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackHistoryInteractorImpl(private val repository: TrackHistoryRepository) :
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