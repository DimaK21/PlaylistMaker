package ru.kryu.playlistmaker.playlists.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

class PlaylistsInteractorImpl(private val playlistsRepository: PlaylistsRepository) :
    PlaylistsInteractor {
    override fun getPlaylists(): Flow<List<Playlist>> {
        return playlistsRepository.getPlaylists()
    }
}