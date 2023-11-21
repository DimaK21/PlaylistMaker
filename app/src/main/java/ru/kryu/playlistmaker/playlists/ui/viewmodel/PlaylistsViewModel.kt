package ru.kryu.playlistmaker.playlists.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.playlists.ui.mapper.PlaylistItemUiMapper
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(private val playlistsInteractor: PlaylistsInteractor) : ViewModel() {

    private val playlistsMutableLiveData = MutableLiveData<PlaylistsState>()
    val playlistsLiveData: LiveData<PlaylistsState> = playlistsMutableLiveData

    private fun setState(state: PlaylistsState) {
        playlistsMutableLiveData.postValue(state)
    }

    fun viewCreated() {
        setState(PlaylistsState.Empty)
        viewModelScope.launch(Dispatchers.IO) {
            playlistsInteractor.getPlaylists().collect { playlists ->
                handleResult(playlists)
            }
        }
    }

    private fun handleResult(playlists: List<Playlist>) {
        if (playlists.isEmpty()) {
            setState(PlaylistsState.Empty)
        } else {
            val playlistsUi = playlists.map { playlist ->
                PlaylistItemUiMapper.map(playlist)
            }
            setState(PlaylistsState.Content(playlistsUi))
        }
    }
}