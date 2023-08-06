package ru.kryu.playlistmaker.search.ui

import ru.kryu.playlistmaker.search.ui.models.TrackForUi

sealed interface TrackSearchState {
    object Loading : TrackSearchState

    data class Content(
        val tracks: List<TrackForUi>
    ) : TrackSearchState

    data class Error(
        val errorMessage: String
    ) : TrackSearchState

    data class Empty(
        val message: String
    ) : TrackSearchState

    data class History(
        val tracks: List<TrackForUi>
    ) : TrackSearchState
}