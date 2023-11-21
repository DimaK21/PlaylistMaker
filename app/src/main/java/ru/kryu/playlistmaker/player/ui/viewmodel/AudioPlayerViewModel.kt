package ru.kryu.playlistmaker.player.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesInteractor
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistInteractor
import ru.kryu.playlistmaker.playlists.domain.api.PlaylistsInteractor
import ru.kryu.playlistmaker.playlists.ui.mapper.PlaylistItemUiMapper
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiMapper
import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.ui.viewmodel.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    private val mediaPlayerInteractor: PlayerInteractor,
    private val favouritesInteractor: FavouritesInteractor,
    private val playlistsInteractor: PlaylistsInteractor,
    private val trackInPlaylistInteractor: TrackInPlaylistInteractor,
    state: SavedStateHandle,
) : ViewModel() {

    private val track: TrackForUi = state.get<TrackForUi>(TRACK)!!

    private var mutablePlayerStateLiveData = MutableLiveData<PlayerState>()
    val playerStateLiveData: LiveData<PlayerState> = mutablePlayerStateLiveData

    private var mutablePlayerPositionLiveData = MutableLiveData<String>()
    val playerPositionLiveData: LiveData<String> = mutablePlayerPositionLiveData

    private var mutableIsFavouriteLiveData = MutableLiveData<Boolean>()
    val isFavouriteLiveData: LiveData<Boolean> = mutableIsFavouriteLiveData

    private val listPlaylistsMutableLiveData = MutableLiveData<List<PlaylistItemUi>>()
    val listPlaylistsLiveData: LiveData<List<PlaylistItemUi>> = listPlaylistsMutableLiveData

    private val messageLiveData = SingleLiveEvent<TrackInPlaylistState>()
    fun observeMessageLiveData(): LiveData<TrackInPlaylistState> = messageLiveData

    private var timerJob: Job? = null

    init {
        changeState(PlayerState.STATE_DEFAULT)

        mediaPlayerInteractor.preparePlayer(track.previewUrl)

        val onPreparedListener = object : PlayerInteractor.PreparedListener {
            override fun setOnPreparedListener() {
                changeState(PlayerState.STATE_PREPARED)
            }
        }
        mediaPlayerInteractor.setOnPreparedListener(onPreparedListener)

        val onCompletionListener = object : PlayerInteractor.CompletionListener {
            override fun setOnCompletionListener() {
                changeState(PlayerState.STATE_PREPARED)
                mutablePlayerPositionLiveData.value = "00:00"
            }
        }
        mediaPlayerInteractor.setOnCompletionListener(onCompletionListener)
        mutableIsFavouriteLiveData.postValue(track.isFavorite)
    }

    private fun timerUpdate() {
        timerJob = viewModelScope.launch {
            while (mutablePlayerStateLiveData.value == PlayerState.STATE_PLAYING) {
                mutablePlayerPositionLiveData.value = getCurrentPosition()
                delay(DELAY_MILLIS)
            }
        }
    }

    fun onPlayerButtonClick() {
        when (mutablePlayerStateLiveData.value) {
            PlayerState.STATE_PLAYING -> pausePlayer()
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> startPlayer()
            else -> Unit
        }
    }

    fun noScreen() {
        pausePlayer()
    }

    private fun startPlayer() {
        mediaPlayerInteractor.startPlayer()
        changeState(PlayerState.STATE_PLAYING)
        timerUpdate()
    }

    private fun pausePlayer() {
        mediaPlayerInteractor.pausePlayer()
        changeState(PlayerState.STATE_PAUSED)
        timerJob?.cancel()
    }

    private fun changeState(playerState: PlayerState) {
        mutablePlayerStateLiveData.value = playerState
    }

    private fun getCurrentPosition(): String {
        return SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayerInteractor.currentPosition())
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayerInteractor.stopPlayer()
    }

    fun onFavoriteClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            if (track.isFavorite) {
                track.isFavorite = false
                favouritesInteractor.removeTrack(TrackForUiMapper.map(track))
                mutableIsFavouriteLiveData.postValue(false)
            } else {
                track.isFavorite = true
                favouritesInteractor.addTrack(TrackForUiMapper.map(track))
                mutableIsFavouriteLiveData.postValue(true)
            }
        }
    }

    fun onAddButtonClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsInteractor.getPlaylists().collect { playlists ->
                val playlistsUi = playlists.map { item ->
                    PlaylistItemUiMapper.map(item)
                }
                listPlaylistsMutableLiveData.postValue(playlistsUi)
            }
        }
    }

    fun onPlaylistClicked(playlistItemUi: PlaylistItemUi) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!trackInPlaylistInteractor.isTrackInDb(idTrack = track.trackId)) {
                favouritesInteractor.addTrack(TrackForUiMapper.map(track))
            }
            val isAdded = trackInPlaylistInteractor.addTrackInPlaylist(
                playlistId = playlistItemUi.playlistId,
                trackId = track.trackId
            )
            if (isAdded) {
                messageLiveData.postValue(TrackInPlaylistState.TrackInPlaylistAdded(playlistItemUi.playlistName))
            } else {
                messageLiveData.postValue(
                    TrackInPlaylistState.TrackInPlaylistNotAdded(
                        playlistItemUi.playlistName
                    )
                )
            }
        }
    }

    companion object {
        private const val DELAY_MILLIS = 300L
        private const val TRACK = "TRACK"
    }
}