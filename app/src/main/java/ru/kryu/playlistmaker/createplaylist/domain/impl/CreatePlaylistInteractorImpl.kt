package ru.kryu.playlistmaker.createplaylist.domain.impl

import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistRepository
import ru.kryu.playlistmaker.playlist.domain.model.Playlist

class CreatePlaylistInteractorImpl(private val createPlaylistRepository: CreatePlaylistRepository) :
    CreatePlaylistInteractor {
    override suspend fun createPlaylist(playlist: Playlist) {
        createPlaylistRepository.createPlaylist(playlist)
    }
}