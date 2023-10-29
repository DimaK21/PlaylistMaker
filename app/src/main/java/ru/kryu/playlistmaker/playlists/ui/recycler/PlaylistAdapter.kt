package ru.kryu.playlistmaker.playlists.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.playlistmaker.databinding.PlaylistItemBinding
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

class PlaylistAdapter : RecyclerView.Adapter<PlaylistViewHolder>() {

    var playlists = ArrayList<PlaylistItemUi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(PlaylistItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun getItemCount(): Int = playlists.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(playlists[position])
    }
}