package ru.kryu.playlistmaker.settings.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.app.App
import ru.kryu.playlistmaker.databinding.ActivitySettingsBinding
import ru.kryu.playlistmaker.settings.ui.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getViewModelFactory()
        )[SettingsViewModel::class.java]

        binding.buttonBackSettings.setOnClickListener {
            finish()
        }

        binding.themeSwitcher.isChecked = (applicationContext as App).darkTheme
        binding.themeSwitcher.setOnCheckedChangeListener { swither, checked ->
            (applicationContext as App).switchTheme(checked)
            val sharedPrefs = getSharedPreferences(App.USER_PREFERENCES, MODE_PRIVATE)
            sharedPrefs.edit()
                .putBoolean(App.DARK_THEME_KEY, binding.themeSwitcher.isChecked)
                .apply()
        }

        binding.shareFrame.setOnClickListener {
            viewModel.doActionSend(getString(R.string.url_practicum))
        }

        binding.supportFrame.setOnClickListener {
            viewModel.doActionSendTo(
                arrayOf(getString(R.string.email_of_developer)),
                getString(R.string.title_mail_to_developer),
                getString(R.string.thanks_to_developer)
            )
        }

        binding.agreementFrame.setOnClickListener {
            viewModel.doActionView(getString(R.string.url_user_agreement))
        }
    }
}