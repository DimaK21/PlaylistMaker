package ru.kryu.playlistmaker.player.ui.view_model

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private val timerRunnable = Runnable {
        timerUpdate()
    }

    private fun timerUpdate() {
        mutablePlayerPositionLiveData.value = getCurrentPosition()
        handlerMainLooper.postDelayed(timerRunnable, DELAY_MILLIS)
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
                handlerMainLooper.removeCallbacks(timerRunnable)
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
        handlerMainLooper.removeCallbacks(timerRunnable)
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
        handlerMainLooper.removeCallbacks(timerRunnable)
        mediaPlayerInteractor.stopPlayer()
    }

    companion object {
        private const val DELAY_MILLIS = 300L
    }
}