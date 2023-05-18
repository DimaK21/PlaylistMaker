package ru.kryu.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.track_search, parent, false)
) {
    private val trackNameTv = itemView.findViewById<TextView>(R.id.track_name)
    private val artistNameTv = itemView.findViewById<TextView>(R.id.artist_name)
    private val trackTimeTv = itemView.findViewById<TextView>(R.id.track_time)
    private val imageTrackIv = itemView.findViewById<ImageView>(R.id.image_track)

    fun bind(track: Track) {
        trackNameTv.text = track.trackName
        artistNameTv.text = track.artistName
        artistNameTv.requestLayout()
        trackTimeTv.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.search_placeholder)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.image_track_corners)))
            .into(imageTrackIv)
    }
}