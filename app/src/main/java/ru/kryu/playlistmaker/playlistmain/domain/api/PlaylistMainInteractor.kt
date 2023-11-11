package ru.kryu.playlistmaker.playlistmain.domain.api

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain

interface PlaylistMainInteractor {

    fun getPlaylistMain(playlistId: Long): Flow<PlaylistMain>
}