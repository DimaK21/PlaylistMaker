package ru.kryu.playlistmaker.favourite.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kryu.playlistmaker.playlist.domain.model.Playlist

class PlaylistsViewModel : ViewModel() {

    private val listPlaylistsMutableLiveData = MutableLiveData<List<Playlist>>()
    val listPlaylistsLiveData = listPlaylistsMutableLiveData

    init {
        listPlaylistsMutableLiveData.postValue(emptyList<Playlist>())
    }
}