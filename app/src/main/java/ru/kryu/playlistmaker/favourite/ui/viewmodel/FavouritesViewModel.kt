package ru.kryu.playlistmaker.favourite.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesInteractor
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiMapper
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesInteractor: FavouritesInteractor,
) : ViewModel() {

    private val favouritesMutableLiveData = MutableLiveData<FavouritesState>()
    val favouritesLiveData: LiveData<FavouritesState> = favouritesMutableLiveData

    fun onViewCreatedOnScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            favouritesInteractor
                .getTracks()
                .collect { tracks ->
                    handleResult(tracks)
                }
        }
    }

    private fun handleResult(tracks: List<Track>) {
        if (tracks.isEmpty()) {
            setState(FavouritesState.Empty)
        } else {
            val tracksForUi = tracks.map { item ->
                TrackForUiMapper.map(item)
            }
            setState(FavouritesState.Content(tracks = tracksForUi))
        }
    }

    private fun setState(state: FavouritesState) {
        favouritesMutableLiveData.postValue(state)
    }
}