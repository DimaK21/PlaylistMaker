package ru.kryu.playlistmaker.settings.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.app.App
import ru.kryu.playlistmaker.databinding.ActivitySettingsBinding
import ru.kryu.playlistmaker.settings.ui.view_model.DarkThemeState
import ru.kryu.playlistmaker.settings.ui.view_model.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.darkThemeStateLiveData.observe(this) { render(it) }

        binding.buttonBackSettings.setOnClickListener {
            finish()
        }

        binding.themeSwitcher.isChecked =
            (viewModel.darkThemeStateLiveData.value == DarkThemeState.STATE_DARK)
        binding.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.switcherMoved(checked)
        }

        binding.shareFrame.setOnClickListener {
            viewModel.onShareClick(getString(R.string.url_practicum))
        }

        binding.supportFrame.setOnClickListener {
            viewModel.onSupportClick(
                arrayOf(getString(R.string.email_of_developer)),
                getString(R.string.title_mail_to_developer),
                getString(R.string.thanks_to_developer)
            )
        }

        binding.agreementFrame.setOnClickListener {
            viewModel.onAgreementClick(getString(R.string.url_user_agreement))
        }
    }

    private fun render(state: DarkThemeState) {
        when (state) {
            DarkThemeState.STATE_DARK -> (application as App).switchTheme(true)
            DarkThemeState.STATE_LITE -> (application as App).switchTheme(false)
        }
    }
}