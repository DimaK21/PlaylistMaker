package ru.kryu.playlistmaker.player.ui.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.ActivityAudioPlayerBinding
import ru.kryu.playlistmaker.player.ui.view_model.AudioPlayerViewModel
import ru.kryu.playlistmaker.player.ui.view_model.PlayerState
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var track: TrackForUi
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var viewModel: AudioPlayerViewModel

    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private val timerRunnable = Runnable {
        timerUpdate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        track = getTrack()
        viewModel = ViewModelProvider(
            this,
            AudioPlayerViewModel.getViewModelFactory(track.previewUrl)
        )[AudioPlayerViewModel::class.java]
        viewModel.playerStateLiveData.observe(this) { render(it) }
        binding.buttonBackPlayer.setOnClickListener {
            finish()
        }
        initTrackInfo()
        binding.playButton.setOnClickListener {
            viewModel.playbackControl()
        }
    }

    private fun getTrack(): TrackForUi =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TRACK, TrackForUi::class.java)
                ?: TrackToTrackForUi().map(
                    Track()
                )
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(TRACK) ?: TrackToTrackForUi().map(
                Track()
            )
        }

    private fun initTrackInfo() {
        Glide.with(this)
            .load(track.artworkUrl512)
            .placeholder(R.drawable.search_placeholder)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corners_8)))
            .into(binding.albumCover)
        binding.trackNameTv.text = track.trackName
        binding.artistNameTv.text = track.artistName
        binding.fullTrackTimeCurrentTv.text = track.getFormatTrackTimeMillis()
        binding.albumCurrentTv.text = track.collectionName
        if (track.collectionName == "") {
            binding.albumGroup.visibility = View.GONE
        }
        binding.yearCurrentTv.text = track.releaseDate.substring(START_INDEX, END_INDEX)
        binding.genreCurrentTv.text = track.primaryGenreName
        binding.countryCurrentTv.text = track.country
    }

    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerMainLooper.removeCallbacks(timerRunnable)
    }

    private fun timerUpdate() {
        binding.timer.text = viewModel.getCurrentPosition()
        handlerMainLooper.postDelayed(timerRunnable, DELAY_MILLIS)
    }

    private fun render(state: PlayerState) {
        when (state) {
            PlayerState.STATE_DEFAULT -> renderStateDefault()
            PlayerState.STATE_PREPARED -> renderStatePrepared()
            PlayerState.STATE_PLAYING -> renderStatePlaying()
            PlayerState.STATE_PAUSED -> renderStatePaused()
        }
    }

    private fun renderStatePaused() {
        binding.playButton.setImageResource(R.drawable.play_button)
        handlerMainLooper.removeCallbacks(timerRunnable)
    }

    private fun renderStatePlaying() {
        binding.playButton.setImageResource(R.drawable.stop_button)
        timerUpdate()
    }

    private fun renderStatePrepared() {
        binding.playButton.isEnabled = true
        handlerMainLooper.removeCallbacks(timerRunnable)
        binding.timer.text = getString(R.string.thirty_seconds)
        binding.playButton.setImageResource(R.drawable.play_button)
    }

    private fun renderStateDefault() {
        binding.playButton.isEnabled = false
    }

    companion object {
        private const val TRACK = "TRACK"
        private const val START_INDEX = 0
        private const val END_INDEX = 4
        private const val DELAY_MILLIS = 300L
    }

}