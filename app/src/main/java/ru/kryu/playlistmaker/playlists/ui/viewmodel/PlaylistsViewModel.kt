package ru.kryu.playlistmaker.playlists.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor

class PlaylistsViewModel(private val playlistsInteractor: PlaylistsInteractor) : ViewModel() {

    private val listPlaylistsMutableLiveData = MutableLiveData<PlaylistsState>()
    val listPlaylistsLiveData: LiveData<PlaylistsState> = listPlaylistsMutableLiveData

    init {
        setState(PlaylistsState.Empty)

    }

    fun setState(state: PlaylistsState) {
        listPlaylistsMutableLiveData.postValue(state)
    }
}