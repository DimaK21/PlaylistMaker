package ru.kryu.playlistmaker.main.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ru.kryu.playlistmaker.media.ui.activity.MediaActivity
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.search.ui.activity.SearchActivity
import ru.kryu.playlistmaker.settings.ui.activity.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.button_search)
        val buttonMedia = findViewById<Button>(R.id.button_media)
        val buttonSettings = findViewById<Button>(R.id.button_settings)

        buttonSearch.setOnClickListener {
            val searchActivityIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchActivityIntent)
        }

        buttonMedia.setOnClickListener {
            val mediaActivityIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaActivityIntent)
        }

        buttonSettings.setOnClickListener {
            val settingsActivityIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsActivityIntent)
        }
    }
}