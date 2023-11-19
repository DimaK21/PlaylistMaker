package ru.kryu.playlistmaker.search.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.playlistmaker.databinding.TrackSearchBinding
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

open class TrackAdapter(private val onTrackClickListener: OnTrackClickListener? = null) :
    RecyclerView.Adapter<TrackViewHolder>() {

    var trackList = ArrayList<TrackForUi>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return TrackViewHolder(TrackSearchBinding.inflate(layoutInspector, parent, false))
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