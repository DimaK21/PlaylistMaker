package ru.kryu.playlistmaker.search.ui.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.creator.Creator
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiToDomain
import ru.kryu.playlistmaker.search.ui.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var latestSearchText: String? = null
    private val trackSearchInteractor =
        Creator.provideTrackSearchInteractor(getApplication<Application>())
    private val trackHistoryInteractor =
        Creator.provideTrackHistoryInteractor(getApplication<Application>())
    private val handler = Handler(Looper.getMainLooper())

    private val stateLiveData = MutableLiveData<TrackSearchState>()
    fun observeStateLiveData(): LiveData<TrackSearchState> = stateLiveData

    val tracksHistory = getTrackHistory().apply {
        renderState(TrackSearchState.History(this))
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable { searchRequest(changedText) }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY
        handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
    }

    fun searchWithoutDebounce(changedText: String) {
        latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable { searchRequest(changedText) }
        handler.post(searchRunnable)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackSearchState.Loading)
            trackSearchInteractor.searchTracks(
                newSearchText,
                object : TrackSearchInteractor.TrackSearchConsumer {
                    override fun consume(foundTracks: List<Track>?, errorMessage: String?) {
                        val tracks = mutableListOf<TrackForUi>()
                        if (foundTracks != null) {
                            tracks.addAll(foundTracks.map { TrackToTrackForUi().map(it) })
                        }

                        when {
                            errorMessage != null -> {
                                renderState(
                                    TrackSearchState.Error(
                                        errorMessage = getApplication<Application>().getString(R.string.connection_problems)
                                    )
                                )
                            }

                            tracks.isEmpty() -> {
                                renderState(
                                    TrackSearchState.Empty(
                                        message = getApplication<Application>().getString(R.string.nothing_was_found)
                                    )
                                )
                            }

                            else -> {
                                renderState(
                                    TrackSearchState.Content(
                                        tracks = tracks
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }
    }

    private fun renderState(state: TrackSearchState) {
        stateLiveData.postValue(state)
    }

    fun addTrack(track: TrackForUi) {
        tracksHistory.removeIf { it.trackId == track.trackId }
        tracksHistory.add(0, track)
        if (tracksHistory.size > TRACK_HISTORY_SIZE) tracksHistory.removeLast()
        if (stateLiveData.value is TrackSearchState.Content) {
            val list =
                (stateLiveData.value as TrackSearchState.Content).tracks as MutableList<TrackForUi>
            list.remove(track)
            list.add(0, track)
            renderState(TrackSearchState.Content(list))
        } else if (stateLiveData.value is TrackSearchState.History) {
            renderState(TrackSearchState.History(tracksHistory))
        }
    }

    private fun getTrackHistory(): MutableList<TrackForUi> {
        return trackHistoryInteractor.getTrackHistory()
            .map { TrackToTrackForUi().map(it) } as MutableList<TrackForUi>
    }

    private fun saveTrackHistory() {
        trackHistoryInteractor.saveTrackHistory(tracksHistory.map {
            TrackForUiToDomain().map(
                it
            )
        })
    }

    fun clearTrackHistory() {
        tracksHistory.clear()
        trackHistoryInteractor.clearTrackHistory()
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        saveTrackHistory()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
        private const val TRACK_HISTORY_SIZE = 10

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}