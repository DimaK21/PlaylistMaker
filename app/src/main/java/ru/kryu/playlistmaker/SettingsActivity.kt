package ru.kryu.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageView>(R.id.settings_arrow)
        val buttonShare = findViewById<FrameLayout>(R.id.share)
        val buttonSupport = findViewById<FrameLayout>(R.id.support)
        val buttonAgreement = findViewById<FrameLayout>(R.id.agreement)
        val email = getString(R.string.email_of_developer)
        val titleMail = getString(R.string.title_mail_to_developer)
        val urlPracticum = getString(R.string.url_practicum)
        val urlUserAgreement = getString(R.string.url_user_agreement)
        val mailTo = "mailto:"

        buttonBack.setOnClickListener {
            finish()
        }

        buttonShare.setOnClickListener{
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse(mailTo)
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, titleMail)
            startActivity(shareIntent)
        }
    }
}