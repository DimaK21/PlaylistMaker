package ru.kryu.playlistmaker.search.domain

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