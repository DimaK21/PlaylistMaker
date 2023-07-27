package ru.kryu.playlistmaker.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.playlistmaker.presentation.models.TrackForUi

class TrackAdapter(
    private var trackList: ArrayList<TrackForUi>,
    private val onTrackClickListener: OnTrackClickListener? = null
) :
    RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(parent)
    }

    override fun getItemCount(): Int = trackList.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])

        holder.itemView.setOnClickListener {
            onTrackClickListener?.onTrackClick(trackList[position])
        }
    }

    fun interface OnTrackClickListener {
        fun onTrackClick(track: TrackForUi)
    }
}