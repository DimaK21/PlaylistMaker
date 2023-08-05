package ru.kryu.playlistmaker.media.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MediaViewModel(application: Application) : AndroidViewModel(application) {


    companion object{
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MediaViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }
}