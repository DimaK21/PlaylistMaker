package ru.kryu.playlistmaker.playlists.ui.recycler

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.PlaylistItemBinding
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

class PlaylistViewHolder(private val binding: PlaylistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(playlist: PlaylistItemUi) {
        binding.tvPlaylistName.text = playlist.playlistName
        binding.tvPlaylistTracksNumber.text = playlist.getPlaylistTracksNumberText()
        Glide.with(itemView)
            .load(playlist.playlistImage)
            .placeholder(R.drawable.search_placeholder)
            .transform(CenterCrop(), RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corners_8)))
            .into(binding.ivPlaylistImage)
    }
}