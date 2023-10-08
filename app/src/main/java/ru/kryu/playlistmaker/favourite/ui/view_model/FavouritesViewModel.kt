package ru.kryu.playlistmaker.favourite.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.favourite.domain.FavouritesInteractor
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.mapper.TrackToTrackForUi

class FavouritesViewModel(
    private val favouritesInteractor: FavouritesInteractor,
    private val trackToTrackForUi: TrackToTrackForUi,
) : ViewModel() {

    private val favouritesMutableLiveData = MutableLiveData<FavouritesState>()
    val favouritesLiveData = favouritesMutableLiveData

    init {
        viewModelScope.launch {
            favouritesInteractor
                .getTracks()
                .collect { tracks ->
                    handleResult(tracks)
                }
        }
    }

    private fun handleResult(tracks: List<Track>) {
        if (tracks.isEmpty()) {
            renderState(FavouritesState.Empty)
        } else {
            val tracksForUi = tracks.map { item ->
                trackToTrackForUi.map(item)
            }
            renderState(FavouritesState.Content(tracks = tracksForUi))
        }
    }

    private fun renderState(state: FavouritesState) {
        favouritesMutableLiveData.postValue(state)
    }
}