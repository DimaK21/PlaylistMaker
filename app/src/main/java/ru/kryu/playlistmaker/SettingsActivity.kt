package ru.kryu.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageView>(R.id.settings_arrow)
        val buttonShare = findViewById<FrameLayout>(R.id.share)
        val buttonSupport = findViewById<FrameLayout>(R.id.support)
        val buttonAgreement = findViewById<FrameLayout>(R.id.agreement)

        buttonBack.setOnClickListener {
            finish()
        }

        buttonShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.url_practicum))
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, null))
        }

        buttonSupport.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SENDTO)
            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_of_developer)))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.title_mail_to_developer))
            supportIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.thanks_to_developer))
            startActivity(supportIntent)
        }

        buttonAgreement.setOnClickListener {
            val agreementIntent = Intent(Intent.ACTION_VIEW)
            agreementIntent.data = Uri.parse(getString(R.string.url_user_agreement))
            startActivity(agreementIntent)
        }
    }
}