package ru.kryu.playlistmaker

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var track: Track

    private val backArrowIb: ImageButton by lazy { findViewById(R.id.back_arrow) }
    private val albumCoverIv: ImageView by lazy { findViewById(R.id.album_cover) }
    private val trackNameTv: TextView by lazy { findViewById(R.id.track_name_tv) }
    private val artistNameTv: TextView by lazy { findViewById(R.id.artist_name_tv) }
    private val addButton: ImageButton by lazy { findViewById(R.id.add_button) }
    private val playButton: ImageButton by lazy { findViewById(R.id.play_button) }
    private val likeButton: ImageButton by lazy { findViewById(R.id.like_button) }
    private val timerTv: TextView by lazy { findViewById(R.id.timer) }
    private val fullTrackTimeCurrentTv: TextView by lazy { findViewById(R.id.full_track_time_current_tv) }
    private val albumCurrentTv: TextView by lazy { findViewById(R.id.album_current_tv) }
    private val yearCurrentTv: TextView by lazy { findViewById(R.id.year_current_tv) }
    private val genreCurrentTv: TextView by lazy { findViewById(R.id.genre_current_tv) }
    private val countryCurrentTv: TextView by lazy { findViewById(R.id.country_current_tv) }
    private val albumGroup: Group by lazy { findViewById(R.id.album_group) }

    private var playerState = PlayerState.STATE_DEFAULT
    private val mediaPlayer = MediaPlayer()
    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private val timerRunnable = Runnable {
        timerUpdate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        playButton.isEnabled = false
        track = getTrack()
        backArrowIb.setOnClickListener {
            finish()
        }
        initTrackInfo()
        preparePlayer()
        playButton.setOnClickListener {
            playbackControl()
        }
    }

    private fun getTrack(): Track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(TRACK, Track::class.java) ?: Track()
    } else {
        @Suppress("DEPRECATION")
        intent.getParcelableExtra(TRACK) ?: Track()
    }

    private fun initTrackInfo() {
        Glide.with(this)
            .load(track.artworkUrl512)
            .placeholder(R.drawable.search_placeholder)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corners_8)))
            .into(albumCoverIv)
        trackNameTv.text = track.trackName
        artistNameTv.text = track.artistName
        fullTrackTimeCurrentTv.text = track.getFormatTrackTimeMillis()
        albumCurrentTv.text = track.collectionName
        if (track.collectionName == "") {
            albumGroup.visibility = View.GONE
        }
        yearCurrentTv.text = track.releaseDate.substring(START_INDEX, END_INDEX)
        genreCurrentTv.text = track.primaryGenreName
        countryCurrentTv.text = track.country
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = PlayerState.STATE_PREPARED
            playButton.isEnabled = true
        }
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.play_button)
            playerState = PlayerState.STATE_PREPARED
            handlerMainLooper.removeCallbacks(timerRunnable)
            timerTv.text = getString(R.string.thirty_seconds)
        }
    }

    private fun playbackControl() {
        when (playerState) {
            PlayerState.STATE_PLAYING -> pausePlayer()
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> startPlayer()
            else -> Unit
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.stop_button)
        playerState = PlayerState.STATE_PLAYING
        timerUpdate()
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.play_button)
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
        mediaPlayer.release()
    }

    private fun timerUpdate() {
        timerTv.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
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