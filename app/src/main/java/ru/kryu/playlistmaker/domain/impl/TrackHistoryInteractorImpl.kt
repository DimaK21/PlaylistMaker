package ru.kryu.playlistmaker.domain.impl

import ru.kryu.playlistmaker.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.domain.api.TrackHistoryRepository
import ru.kryu.playlistmaker.domain.models.Track

class TrackHistoryInteractorImpl(private val repository: TrackHistoryRepository): TrackHistoryInteractor {

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