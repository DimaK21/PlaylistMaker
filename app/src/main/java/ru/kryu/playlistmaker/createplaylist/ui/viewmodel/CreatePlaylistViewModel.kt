package ru.kryu.playlistmaker.createplaylist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.playlist.domain.model.Playlist

class CreatePlaylistViewModel(private val createPlaylistInteractor: CreatePlaylistInteractor) :
    ViewModel() {

    fun onButtonSaveClick(
        playlistName: String,
        playlistDescription: String,
        playlistCoverPath: String,
    ) {
        val playlist = Playlist(
            playlistName = playlistName,
            playlistDescription = playlistDescription,
            playlistCoverPath = playlistCoverPath
        )
        viewModelScope.launch(Dispatchers.IO) {
            createPlaylistInteractor.createPlaylist(playlist)
        }
    }
}