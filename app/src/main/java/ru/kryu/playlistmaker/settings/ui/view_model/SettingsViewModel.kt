package ru.kryu.playlistmaker.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.kryu.playlistmaker.R
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

    fun onShareClick() {
        actionSend.execute(text = getApplication<Application>().getString(R.string.url_practicum))
    }

    fun onSupportClick() {
        actionSendTo.execute(
            email = arrayOf(getApplication<Application>().getString(R.string.email_of_developer)),
            subject = getApplication<Application>().getString(R.string.title_mail_to_developer),
            text = getApplication<Application>().getString(R.string.thanks_to_developer)
        )
    }

    fun onAgreementClick() {
        actionView.execute(url = getApplication<Application>().getString(R.string.url_user_agreement))
    }


    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModel(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
            }
        }
    }
}