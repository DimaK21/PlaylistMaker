package ru.kryu.playlistmaker.playlistmain.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.ui.model.PlaylistMainItem
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiMapper
import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendUseCase

class PlaylistMainViewModel(
    private val playlistId: Long,
    private val playlistMainInteractor: PlaylistMainInteractor,
    private val actionSendUseCase: ActionSendUseCase,
) : ViewModel() {

    private var mutablePlaylistMainLiveData = MutableLiveData<PlaylistMainItem>()
    val playlistMainLiveData: LiveData<PlaylistMainItem> = mutablePlaylistMainLiveData
    private lateinit var jobGetPlaylistInfo: Job

    init {
        jobGetPlaylistInfo = viewModelScope.launch(Dispatchers.IO) {
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

    fun shareClicked(tracksInPlaylist: String) {
        val message = """
            |${playlistMainLiveData.value?.playlistName}
            |${playlistMainLiveData.value?.playlistDescription}
            |$tracksInPlaylist
            |${trackListStringHandle()}
        """.trimMargin()
        actionSendUseCase.execute(message)
    }

    private fun trackListStringHandle(): String {
        val stringBuilder = StringBuilder()
        val list = playlistMainLiveData.value?.tracks!!
        for ((index, value) in list.withIndex()) {
            stringBuilder.append("${index + 1}. ${value.artistName} - ${value.trackName} (${value.getFormatTrackTimeMillis()})\n")
        }
        return stringBuilder.toString()
    }

    fun deletePlaylistClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            jobGetPlaylistInfo?.cancelAndJoin()
            playlistMainInteractor.deletePlaylist(playlistId)
        }
    }
}