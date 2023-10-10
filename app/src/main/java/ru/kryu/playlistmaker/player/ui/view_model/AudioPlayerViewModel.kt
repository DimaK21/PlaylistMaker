package ru.kryu.playlistmaker.player.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.favourite.domain.FavouritesInteractor
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiMapper
import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    private val track: TrackForUi,
    private val mediaPlayerInteractor: PlayerInteractor,
    private val favouritesInteractor: FavouritesInteractor,
) : ViewModel() {

    private var mutablePlayerStateLiveData = MutableLiveData<PlayerState>()
    val playerStateLiveData: LiveData<PlayerState> = mutablePlayerStateLiveData

    private var mutablePlayerPositionLiveData = MutableLiveData<String>()
    val playerPositionLiveData: LiveData<String> = mutablePlayerPositionLiveData

    private var mutableIsFavouriteLiveData = MutableLiveData<Boolean>()
    val isFavouriteLiveData: LiveData<Boolean> = mutableIsFavouriteLiveData

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
                favouritesInteractor.removeTrack(TrackForUiMapper.map(track))
                track.isFavorite = false
                mutableIsFavouriteLiveData.postValue(false)
            } else {
                favouritesInteractor.addTrack(TrackForUiMapper.map(track))
                track.isFavorite = true
                mutableIsFavouriteLiveData.postValue(true)
            }
        }
    }

    companion object {
        private const val DELAY_MILLIS = 300L
    }
}