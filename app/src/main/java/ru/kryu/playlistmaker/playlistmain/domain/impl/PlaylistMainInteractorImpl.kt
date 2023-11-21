package ru.kryu.playlistmaker.playlistmain.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain
import javax.inject.Inject

class PlaylistMainInteractorImpl @Inject constructor(private val playlistMainRepository: PlaylistMainRepository) :
    PlaylistMainInteractor {
    override fun getPlaylistMain(playlistId: Long): Flow<PlaylistMain> {
        return playlistMainRepository.getPlaylistMain(playlistId)
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long) {
        playlistMainRepository.removeTrackFromPlaylist(playlistId, trackId)
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        playlistMainRepository.deletePlaylist(playlistId)
    }
}