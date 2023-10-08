package ru.kryu.playlistmaker.favourite.ui.view_model

import ru.kryu.playlistmaker.search.ui.models.TrackForUi

sealed interface FavouritesState {
    object Empty : FavouritesState
    data class Content(val tracks: List<TrackForUi>) : FavouritesState
}