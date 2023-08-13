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

    private val mutableIsClickAllowedLiveData = MutableLiveData<Boolean>()
    val isClickAllowedLiveData: LiveData<Boolean> = mutableIsClickAllowedLiveData
    private var isClickAllowed = true

    init {
        val list = getTrackHistory()
        if (list.isEmpty()) {
            renderState(TrackSearchState.Content(emptyList()))
        } else {
            renderState(TrackSearchState.History(list))
        }
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        latestSearchText = changedText
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        val searchRunnable = Runnable { searchRequest(changedText) }
        val postTime = SystemClock.uptimeMillis() + SEARCH_DEBOUNCE_DELAY_MILLIS
        handler.postAtTime(searchRunnable, SEARCH_REQUEST_TOKEN, postTime)
    }

    fun searchWithoutDebounce(changedText: String) {
        mutableIsClickAllowedLiveData.value = clickDebounce()
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

    fun onTrackClick(track: TrackForUi) {
        mutableIsClickAllowedLiveData.value = clickDebounce()
        trackHistoryInteractor.addTrack(TrackForUiToDomain().map(track))
        if (stateLiveData.value is TrackSearchState.History) {
            renderState(TrackSearchState.History(getTrackHistory()))
        }
    }

    private fun getTrackHistory(): MutableList<TrackForUi> {
        return trackHistoryInteractor.getTrackHistory()
            .map { TrackToTrackForUi().map(it) } as MutableList<TrackForUi>
    }

    private fun saveTrackHistory() {
        trackHistoryInteractor.saveTrackHistory()
    }

    fun onClearTrackHistoryClick() {
        trackHistoryInteractor.clearTrackHistory()
        renderState(TrackSearchState.Content(emptyList()))
    }

    fun onClearButtonClick() {
        renderState(TrackSearchState.History(getTrackHistory()))
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
        saveTrackHistory()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed(
                { isClickAllowed = true },
                CLICK_DEBOUNCE_DELAY_MILLIS
            )
        }
        return current
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}