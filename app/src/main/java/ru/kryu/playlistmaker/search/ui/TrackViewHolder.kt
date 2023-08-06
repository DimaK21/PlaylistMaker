package ru.kryu.playlistmaker.search.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.TrackSearchBinding
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class TrackViewHolder(private val binding: TrackSearchBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(track: TrackForUi) {
        binding.trackName.text = track.trackName
        binding.artistName.text = track.artistName
        binding.trackTime.text = track.getFormatTrackTimeMillis()
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.search_placeholder)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.image_track_corners)))
            .into(binding.imageTrack)
    }
}