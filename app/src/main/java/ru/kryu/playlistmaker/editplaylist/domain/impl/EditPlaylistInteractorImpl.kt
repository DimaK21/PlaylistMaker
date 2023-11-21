package ru.kryu.playlistmaker.editplaylist.domain.impl

import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistInteractor
import ru.kryu.playlistmaker.editplaylist.domain.api.EditPlaylistRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import javax.inject.Inject

class EditPlaylistInteractorImpl @Inject constructor(private val editPlaylistRepository: EditPlaylistRepository) :
    EditPlaylistInteractor {
    override suspend fun updatePlaylist(playlist: Playlist) {
        editPlaylistRepository.updatePlaylist(playlist)
    }
}