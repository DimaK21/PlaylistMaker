package ru.kryu.playlistmaker.settings.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.app.App
import ru.kryu.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.url_practicum))
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, null))
        }

        binding.supportFrame.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SENDTO)
            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(getString(R.string.email_of_developer))
            )
            supportIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.title_mail_to_developer)
            )
            supportIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.thanks_to_developer))
            startActivity(supportIntent)
        }

        binding.agreementFrame.setOnClickListener {
            val agreementIntent = Intent(Intent.ACTION_VIEW)
            agreementIntent.data = Uri.parse(getString(R.string.url_user_agreement))
            startActivity(agreementIntent)
        }
    }
}