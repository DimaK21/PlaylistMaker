package ru.kryu.playlistmaker.settings.ui.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.kryu.playlistmaker.creator.Creator

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val actionSend =
        Creator.provideActionSendUseCase(getApplication<Application>().applicationContext)
    private val actionSendTo = Creator.provideActionSendToUseCase(getApplication<Application>())
    private val actionView = Creator.provideActionViewUseCase(getApplication<Application>())
    val handler = Handler(Looper.getMainLooper())

    fun doActionSend(text: String) {
        actionSend.execute(text = text)
    }

    fun doActionSendTo(email: Array<String>, subject: String, text: String) {
        actionSendTo.execute(email = email, subject = subject, text = text)
    }

    fun doActionView(url: String) {
        actionView.execute(url = url)
    }


    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
            }
        }
    }
}