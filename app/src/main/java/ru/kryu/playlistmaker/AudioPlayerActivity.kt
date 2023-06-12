package ru.kryu.playlistmaker

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            track = intent.getParcelableExtra(TRACK, Track::class.java) ?: Track()
        } else {
            @Suppress("DEPRECATION")
            track = intent.getParcelableExtra(TRACK) ?: Track()
        }

        backArrowIb.setOnClickListener { finish() }
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

    companion object {
        private const val TRACK = "TRACK"
        private const val START_INDEX = 0
        private const val END_INDEX = 4
    }
}