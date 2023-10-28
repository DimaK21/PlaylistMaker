package ru.kryu.playlistmaker.playlists.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

class PlaylistsViewModel : ViewModel() {

    private val listPlaylistsMutableLiveData = MutableLiveData<List<Playlist>>()
    val listPlaylistsLiveData = listPlaylistsMutableLiveData

    init {
        listPlaylistsMutableLiveData.postValue(emptyList())
    }
}