package ru.kryu.playlistmaker.favourite.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class FavouritesViewModel : ViewModel() {

    private val listFavouritesMutableLiveData = MutableLiveData<List<TrackForUi>>()
    val listFavouritesLiveData = listFavouritesMutableLiveData

    init {
        listFavouritesMutableLiveData.postValue(emptyList<TrackForUi>())
    }
}