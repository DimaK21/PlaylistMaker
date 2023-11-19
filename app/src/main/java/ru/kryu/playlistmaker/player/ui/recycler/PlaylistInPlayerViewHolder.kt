package ru.kryu.playlistmaker.player.ui.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.PlaylistItemSmallBinding
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

class PlaylistInPlayerViewHolder(private val binding: PlaylistItemSmallBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(playlist: PlaylistItemUi) {
        binding.tvPlaylistNameSmall.text = playlist.playlistName
        val numberOfSongs = "${playlist.playlistTracksNumber} ${
            itemView.resources.getQuantityString(
                R.plurals.number_of_tracks,
                playlist.playlistTracksNumber
            )
        }"
        binding.tvPlaylistSongsNumberSmall.text = numberOfSongs
        Glide.with(itemView)
            .load(playlist.playlistImage)
            .placeholder(R.drawable.search_placeholder)
            .transform(
                CenterCrop(),
                RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.image_track_corners))
            )
            .into(binding.ivPlaylistImageSmall)
    }
}