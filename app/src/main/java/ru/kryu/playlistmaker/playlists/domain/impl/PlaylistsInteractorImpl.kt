package ru.kryu.playlistmaker.playlists.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import javax.inject.Inject

class PlaylistsInteractorImpl @Inject constructor(private val playlistsRepository: PlaylistsRepository) :
    PlaylistsInteractor {
    override fun getPlaylists(): Flow<List<Playlist>> {
        return playlistsRepository.getPlaylists()
    }
}