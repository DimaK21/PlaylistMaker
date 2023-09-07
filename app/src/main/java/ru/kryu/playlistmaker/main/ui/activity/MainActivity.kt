package ru.kryu.playlistmaker.main.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kryu.playlistmaker.databinding.ActivityMainBinding
import ru.kryu.playlistmaker.favourite.ui.activity.MediaActivity
import ru.kryu.playlistmaker.search.ui.activity.SearchActivity
import ru.kryu.playlistmaker.settings.ui.activity.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            val searchActivityIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchActivityIntent)
        }

        binding.buttonMedia.setOnClickListener {
            val mediaActivityIntent = Intent(this, MediaActivity::class.java)
            startActivity(mediaActivityIntent)
        }

        binding.buttonSettings.setOnClickListener {
            val settingsActivityIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsActivityIntent)
        }
    }
}