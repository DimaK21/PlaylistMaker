package ru.kryu.playlistmaker.createplaylist.domain.impl

import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

class CreatePlaylistInteractorImpl(private val createPlaylistRepository: CreatePlaylistRepository) :
    CreatePlaylistInteractor {
    override suspend fun createPlaylist(playlist: Playlist) {
        createPlaylistRepository.createPlaylist(playlist)
    }

    override suspend fun saveImageToPrivateStorage(path: String, imageId: String) {
        createPlaylistRepository.saveImageToPrivateStorage(path, imageId)
    }
}