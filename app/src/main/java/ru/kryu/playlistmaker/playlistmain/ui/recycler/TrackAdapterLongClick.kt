package ru.kryu.playlistmaker.playlistmain.ui.recycler

import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.ui.recycler.TrackAdapter
import ru.kryu.playlistmaker.search.ui.recycler.TrackViewHolder

class TrackAdapterLongClick(
    onTrackClickListener: OnTrackClickListener? = null,
    private val onTrackLongClickListener: OnTrackLongClickListener? = null
) : TrackAdapter(onTrackClickListener) {

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.setOnLongClickListener {
            onTrackLongClickListener?.onTrackLongClick(trackList[position])
            true
        }
    }

    fun interface OnTrackLongClickListener {
        fun onTrackLongClick(track: TrackForUi)
    }
}