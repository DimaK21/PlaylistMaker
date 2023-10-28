package ru.kryu.playlistmaker.playlists.ui.fragment.recycler

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.PlaylistItemBinding
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

class PlaylistViewHolder(private val binding: PlaylistItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(playlist: PlaylistItemUi) {
        binding.tvPlaylistName.text = playlist.playlistName
        binding.tvPlaylistTracksNumber.text = playlist.playlistTracksNumber.toString()
        Glide.with(itemView)
            .load(playlist.playlistImage)
            .placeholder(R.drawable.search_placeholder)
            .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.edit_text_corner_radius)))
            .into(binding.ivPlaylistImage)
    }
}