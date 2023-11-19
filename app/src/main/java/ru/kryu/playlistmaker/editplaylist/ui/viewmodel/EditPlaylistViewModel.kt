package ru.kryu.playlistmaker.editplaylist.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.createplaylist.ui.viewmodel.CreatePlaylistViewModel
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistInteractor
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

class EditPlaylistViewModel(
    createPlaylistInteractor: CreatePlaylistInteractor,
    private val editPlaylistInteractor: EditPlaylistInteractor,
) : CreatePlaylistViewModel(createPlaylistInteractor) {

    override fun onButtonSaveClick(
        playlistId: Long?,
        playlistName: String,
        playlistDescription: String,
        playlistCoverPath: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            editPlaylistInteractor.updatePlaylist(
                Playlist(
                    playlistId = playlistId,
                    playlistName = playlistName,
                    playlistDescription = playlistDescription,
                    playlistCoverPath = playlistCoverPath
                )
            )
        }
    }
}