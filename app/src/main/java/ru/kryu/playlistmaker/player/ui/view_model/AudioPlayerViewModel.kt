package ru.kryu.playlistmaker.player.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(
    trackUrl: String,
    private val mediaPlayerInteractor: PlayerInteractor
) : ViewModel() {

    private var mutablePlayerStateLiveData = MutableLiveData<PlayerState>()
    val playerStateLiveData: LiveData<PlayerState> = mutablePlayerStateLiveData

    private var mutablePlayerPositionLiveData = MutableLiveData<String>()
    val playerPositionLiveData: LiveData<String> = mutablePlayerPositionLiveData

    private var timerJob: Job? = null

    private fun timerUpdate() {
        timerJob = viewModelScope.launch {
            while (mutablePlayerStateLiveData.value == PlayerState.STATE_PLAYING) {
                mutablePlayerPositionLiveData.value = getCurrentPosition()
                delay(DELAY_MILLIS)
            }
        }
    }

    init {
        changeState(PlayerState.STATE_DEFAULT)

        mediaPlayerInteractor.preparePlayer(trackUrl)

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

    companion object {
        private const val DELAY_MILLIS = 300L
    }
}