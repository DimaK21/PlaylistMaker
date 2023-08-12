package ru.kryu.playlistmaker.player.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.kryu.playlistmaker.creator.Creator
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerViewModel(private val trackUrl: String) : ViewModel() {

    private var mutablePlayerStateLiveData = MutableLiveData<PlayerState>()
    val playerStateLiveData: LiveData<PlayerState> = mutablePlayerStateLiveData
    private val mediaPlayerInteractor = Creator.providePlayerInteractor()

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

    fun noScreen(){
        pausePlayer()
    }

    private fun startPlayer() {
        mediaPlayerInteractor.startPlayer()
        changeState(PlayerState.STATE_PLAYING)
    }

    private fun pausePlayer() {
        mediaPlayerInteractor.pausePlayer()
        changeState(PlayerState.STATE_PAUSED)
    }

    private fun changeState(playerState: PlayerState) {
        mutablePlayerStateLiveData.value = playerState
    }

    fun getCurrentPosition(): String {
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
        fun getViewModelFactory(trackUrl: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AudioPlayerViewModel(trackUrl)
            }
        }
    }
}