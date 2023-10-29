package ru.kryu.playlistmaker.player.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.playlistmaker.databinding.PlaylistItemSmallBinding
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

class PlaylistInPlayerAdapter(private val onPlaylistClickListener: OnPlaylistClickListener? = null) :
    RecyclerView.Adapter<PlaylistInPlayerViewHolder>() {

    var playlists = ArrayList<PlaylistItemUi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistInPlayerViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistInPlayerViewHolder(
            PlaylistItemSmallBinding.inflate(
                layoutInspector,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = playlists.size

    override fun onBindViewHolder(holder: PlaylistInPlayerViewHolder, position: Int) {
        holder.bind(playlists[position])

        holder.itemView.setOnClickListener {
            onPlaylistClickListener?.onPlaylistClick(playlists[position])
        }
    }

    fun interface OnPlaylistClickListener {
        fun onPlaylistClick(playlist: PlaylistItemUi)
    }
}