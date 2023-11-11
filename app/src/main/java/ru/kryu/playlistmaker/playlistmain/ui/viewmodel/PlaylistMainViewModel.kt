package ru.kryu.playlistmaker.playlistmain.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.ui.model.PlaylistMainItem
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiMapper
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class PlaylistMainViewModel(
    private val playlistId: Long,
    private val playlistMainInteractor: PlaylistMainInteractor
) : ViewModel() {

    private var mutablePlaylistMainLiveData = MutableLiveData<PlaylistMainItem>()
    val playlistMainLiveData: LiveData<PlaylistMainItem> = mutablePlaylistMainLiveData

    fun initPlaylistInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistMainInteractor.getPlaylistMain(playlistId)
                .distinctUntilChanged()
                .collect { playlistMain ->
                    mutablePlaylistMainLiveData.postValue(
                        PlaylistMainItem(
                            playlistId = playlistMain.playlist.playlistId!!,
                            playlistName = playlistMain.playlist.playlistName,
                            playlistDescription = playlistMain.playlist.playlistDescription,
                            playlistCoverPath = playlistMain.playlist.playlistCoverPath,
                            countTracks = playlistMain.playlist.countTracks,
                            playlistDuration = playlistMain.tracks.sumOf { track ->
                                track.trackTimeMillis
                            } / 1000 / 60,
                            tracks = playlistMain.tracks.map { track ->
                                TrackForUiMapper.map(track)
                            }
                        )
                    )
                }
        }
    }

    fun trackRemoved(track: TrackForUi) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistMainInteractor.removeTrackFromPlaylist(playlistId, track.trackId)
        }
    }

}