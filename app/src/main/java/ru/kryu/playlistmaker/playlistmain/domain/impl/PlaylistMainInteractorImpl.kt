package ru.kryu.playlistmaker.playlistmain.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain

class PlaylistMainInteractorImpl(private val playlistMainRepository: PlaylistMainRepository) :
    PlaylistMainInteractor {
    override fun getPlaylistMain(playlistId: Long): Flow<PlaylistMain> {
        return playlistMainRepository.getPlaylistMain(playlistId)
    }
}