package ru.kryu.playlistmaker.search.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.search.domain.api.TrackHistoryInteractor
import ru.kryu.playlistmaker.search.domain.api.TrackSearchInteractor
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiToDomain
import ru.kryu.playlistmaker.search.ui.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class SearchViewModel(
    application: Application,
    private val trackSearchInteractor: TrackSearchInteractor,
    private val trackHistoryInteractor: TrackHistoryInteractor,
    private val trackForUiToDomain: TrackForUiToDomain,
    private val trackToTrackForUi: TrackToTrackForUi,
) : AndroidViewModel(application) {

    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    private val stateLiveData = MutableLiveData<TrackSearchState>()
    fun observeStateLiveData(): LiveData<TrackSearchState> = stateLiveData

    private val toastLiveData = SingleLiveEvent<String?>()
    fun observeToastLiveData(): LiveData<String?> = toastLiveData

    private val mutableIsClickAllowedLiveData = MutableLiveData<Boolean>()
    val isClickAllowedLiveData: LiveData<Boolean> = mutableIsClickAllowedLiveData
    private var isClickAllowed = true

    init {
        renderHistoryCheck()
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }
        latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            searchRequest(changedText)
        }
    }

    fun searchWithoutDebounce(changedText: String) {
        clickDebounce()
        latestSearchText = changedText
        searchRequest(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(TrackSearchState.Loading)
            viewModelScope.launch {
                trackSearchInteractor
                    .searchTracks(newSearchText)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(foundTracks: List<Track>?, errorMessage: String?) {
        val tracks = mutableListOf<TrackForUi>()
        if (foundTracks != null) {
            tracks.addAll(foundTracks.map { trackToTrackForUi.map(it) })
        }

        when {
            errorMessage != null -> {
                renderState(
                    TrackSearchState.Error(
                        errorMessage = getApplication<Application>().getString(R.string.connection_problems)
                    )
                )
                toastLiveData.postValue(errorMessage)
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

    private fun renderState(state: TrackSearchState) {
        stateLiveData.postValue(state)
    }

    fun onTrackClick(track: TrackForUi) {
        clickDebounce()
        trackHistoryInteractor.addTrack(trackForUiToDomain.map(track))
        saveTrackHistory()
        if (stateLiveData.value is TrackSearchState.History) {
            renderState(TrackSearchState.History(getTrackHistory()))
        }
    }

    private fun getTrackHistory(): MutableList<TrackForUi> {
        return trackHistoryInteractor.getTrackHistory()
            .map { trackToTrackForUi.map(it) } as MutableList<TrackForUi>
    }

    private fun saveTrackHistory() {
        trackHistoryInteractor.saveTrackHistory()
    }

    fun onClearTrackHistoryClick() {
        trackHistoryInteractor.clearTrackHistory()
        renderState(TrackSearchState.Content(emptyList()))
    }

    fun onClearButtonClick() {
        renderHistoryCheck()
    }

    private fun renderHistoryCheck() {
        val list = getTrackHistory()
        if (list.isEmpty()) {
            renderState(TrackSearchState.Content(emptyList()))
        } else {
            renderState(TrackSearchState.History(list))
        }
    }

    private fun clickDebounce() {
        if (isClickAllowed) {
            isClickAllowed = false
            mutableIsClickAllowedLiveData.postValue(false)
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
                mutableIsClickAllowedLiveData.postValue(true)
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}