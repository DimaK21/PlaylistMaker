package ru.kryu.playlistmaker.player.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
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
    private val viewModel: AudioPlayerViewModel by viewModel {
        parametersOf(track.previewUrl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        track = getTrack()
        viewModel.playerStateLiveData.observe(this) { render(it) }
        viewModel.playerPositionLiveData.observe(this) { setTimer(it) }
        binding.buttonBackPlayer.setOnClickListener {
            finish()
        }
        initTrackInfo()
        binding.playButton.setOnClickListener {
            viewModel.onPlayerButtonClick()
        }
    }

    private fun setTimer(time: String?) {
        binding.timer.text = time
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
        viewModel.noScreen()
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
    }

    private fun renderStatePlaying() {
        binding.playButton.setImageResource(R.drawable.stop_button)
    }

    private fun renderStatePrepared() {
        binding.playButton.isEnabled = true
        binding.playButton.setImageResource(R.drawable.play_button)
    }

    private fun renderStateDefault() {
        binding.playButton.isEnabled = false
    }

    companion object {
        private const val TRACK = "TRACK"
        private const val START_INDEX = 0
        private const val END_INDEX = 4
    }
}
