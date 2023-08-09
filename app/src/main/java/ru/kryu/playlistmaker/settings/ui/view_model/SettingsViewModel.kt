package ru.kryu.playlistmaker.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.kryu.playlistmaker.creator.Creator

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private var mutableDarkThemeStateLiveData = MutableLiveData<DarkThemeState>()
    val darkThemeStateLiveData: LiveData<DarkThemeState> = mutableDarkThemeStateLiveData

    private val actionSend =
        Creator.provideActionSendUseCase(getApplication<Application>())
    private val actionSendTo = Creator.provideActionSendToUseCase(getApplication<Application>())
    private val actionView = Creator.provideActionViewUseCase(getApplication<Application>())
    private val darkThemeInteractor =
        Creator.provideDarkThemeInteractor(getApplication<Application>())

    init {
        changeState(getDarkTheme())
    }

    fun changeState(isDarkTheme: Boolean) {
        if (isDarkTheme) {
            mutableDarkThemeStateLiveData.value = DarkThemeState.STATE_DARK
        } else {
            mutableDarkThemeStateLiveData.value = DarkThemeState.STATE_LITE
        }
        saveDarkTheme(isDarkTheme)
    }

    private fun getDarkTheme(): Boolean {
        return darkThemeInteractor.getDarkTheme()
    }

    private fun saveDarkTheme(isDarkTheme: Boolean) {
        darkThemeInteractor.saveDarkTheme(isDarkTheme)
    }

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