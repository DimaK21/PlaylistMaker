package ru.kryu.playlistmaker.createplaylist.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import javax.inject.Inject

@HiltViewModel
open class CreatePlaylistViewModel @Inject constructor(private val createPlaylistInteractor: CreatePlaylistInteractor) :
    ViewModel() {

    open fun onButtonSaveClick(
        playlistId: Long?,
        playlistName: String,
        playlistDescription: String,
        playlistCoverPath: String,
    ) {
        val playlist = Playlist(
            playlistId = playlistId,
            playlistName = playlistName,
            playlistDescription = playlistDescription,
            playlistCoverPath = playlistCoverPath
        )
        viewModelScope.launch(Dispatchers.IO) {
            createPlaylistInteractor.createPlaylist(playlist)
        }
    }

    fun mediaPicked(uri: Uri, imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createPlaylistInteractor.saveImageToPrivateStorage(uri.toString(), imageId)
        }
    }
}