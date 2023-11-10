package ru.kryu.playlistmaker.playlistmain.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainInteractor
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain

class PlaylistMainViewModel(
    private val playlistId: Long,
    private val playlistMainInteractor: PlaylistMainInteractor
) : ViewModel() {

    private var mutablePlaylistMainLiveData = MutableLiveData<PlaylistMain>()
    val playlistMainLiveData: LiveData<PlaylistMain> = mutablePlaylistMainLiveData

    fun initPlaylistInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistMainInteractor.getPlaylistMain(playlistId)
                .distinctUntilChanged()
                .collect { playlistMain ->
                    mutablePlaylistMainLiveData.postValue(playlistMain)
                }
        }
    }

}