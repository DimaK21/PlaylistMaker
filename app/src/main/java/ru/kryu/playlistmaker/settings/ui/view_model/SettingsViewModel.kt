package ru.kryu.playlistmaker.settings.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kryu.playlistmaker.settings.domain.api.DarkThemeInteractor
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendToUseCaseImpl
import ru.kryu.playlistmaker.sharing.domain.impl.ActionSendUseCaseImpl
import ru.kryu.playlistmaker.sharing.domain.impl.ActionViewUseCaseImpl

class SettingsViewModel(
    private val actionSend: ActionSendUseCaseImpl,
    private val actionSendTo: ActionSendToUseCaseImpl,
    private val actionView: ActionViewUseCaseImpl,
    private val darkThemeInteractor: DarkThemeInteractor,
) : ViewModel() {

    private var mutableDarkThemeStateLiveData = MutableLiveData<DarkThemeState>()
    val darkThemeStateLiveData: LiveData<DarkThemeState> = mutableDarkThemeStateLiveData

    init {
        changeState(getDarkTheme())
    }

    private fun changeState(isDarkTheme: Boolean) {
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

    fun switcherMoved(isChecked: Boolean) {
        changeState(isDarkTheme = isChecked)
    }

    fun onShareClick(text: String) {
        actionSend.execute(text = text)
    }

    fun onSupportClick(email: Array<String>, subject: String, text: String) {
        actionSendTo.execute(
            email = email,
            subject = subject,
            text = text,
        )
    }

    fun onAgreementClick(url: String) {
        actionView.execute(url = url)
    }
}