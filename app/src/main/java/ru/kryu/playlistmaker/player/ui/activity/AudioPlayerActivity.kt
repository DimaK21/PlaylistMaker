package ru.kryu.playlistmaker.player.ui.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.creator.Creator
import ru.kryu.playlistmaker.databinding.ActivityAudioPlayerBinding
import ru.kryu.playlistmaker.player.domain.api.PlayerInteractor
import ru.kryu.playlistmaker.player.ui.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.player.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var track: TrackForUi
    private lateinit var binding: ActivityAudioPlayerBinding

    private var playerState = PlayerState.STATE_DEFAULT
    private val mediaPlayer = Creator.providePlayerInteractor()
    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private val timerRunnable = Runnable {
        timerUpdate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.isEnabled = false
        track = getTrack()
        binding.buttonBackPlayer.setOnClickListener {
            finish()
        }
        initTrackInfo()
        preparePlayer()
        binding.playButton.setOnClickListener {
            playbackControl()
        }
    }

    private fun getTrack(): TrackForUi =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TRACK, TrackForUi::class.java)
                ?: TrackToTrackForUi().trackToTrackForUi(
                    Track()
                )
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(TRACK) ?: TrackToTrackForUi().trackToTrackForUi(
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

    private fun preparePlayer() {
        mediaPlayer.preparePlayer(track.previewUrl)

        val onPreparedListener = object : PlayerInteractor.PreparedListener {
            override fun setOnPreparedListener() {
                playerState = PlayerState.STATE_PREPARED
                binding.playButton.isEnabled = true
            }
        }
        mediaPlayer.setOnPreparedListener(onPreparedListener)

        val onCompletionListener = object : PlayerInteractor.CompletionListener {
            override fun setOnCompletionListener() {
                binding.playButton.setImageResource(R.drawable.play_button)
                playerState = PlayerState.STATE_PREPARED
                handlerMainLooper.removeCallbacks(timerRunnable)
                binding.timer.text = getString(R.string.thirty_seconds)
            }
        }
        mediaPlayer.setOnCompletionListener(onCompletionListener)
    }

    private fun playbackControl() {
        when (playerState) {
            PlayerState.STATE_PLAYING -> pausePlayer()
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> startPlayer()
            else -> Unit
        }
    }

    private fun startPlayer() {
        mediaPlayer.startPlayer()
        binding.playButton.setImageResource(R.drawable.stop_button)
        playerState = PlayerState.STATE_PLAYING
        timerUpdate()
    }

    private fun pausePlayer() {
        mediaPlayer.pausePlayer()
        binding.playButton.setImageResource(R.drawable.play_button)
        playerState = PlayerState.STATE_PAUSED
        handlerMainLooper.removeCallbacks(timerRunnable)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handlerMainLooper.removeCallbacks(timerRunnable)
        mediaPlayer.stopPlayer()
    }

    private fun timerUpdate() {
        binding.timer.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition())
        handlerMainLooper.postDelayed(timerRunnable, DELAY_MILLIS)
    }

    companion object {
        private const val TRACK = "TRACK"
        private const val START_INDEX = 0
        private const val END_INDEX = 4
        private const val DELAY_MILLIS = 300L
    }

    enum class PlayerState {
        STATE_DEFAULT,
        STATE_PREPARED,
        STATE_PLAYING,
        STATE_PAUSED
    }
}