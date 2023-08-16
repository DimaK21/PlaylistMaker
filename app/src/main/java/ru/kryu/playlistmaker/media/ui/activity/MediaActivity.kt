package ru.kryu.playlistmaker.media.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.media.ui.view_model.MediaViewModel

class MediaActivity : AppCompatActivity() {

    private lateinit var viewModel: MediaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        viewModel = ViewModelProvider(
            this,
            MediaViewModel.getViewModelFactory()
        )[MediaViewModel::class.java]
    }
}