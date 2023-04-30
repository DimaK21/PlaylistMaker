package ru.kryu.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrackAdapter(private val trackList: List<Track>): RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val trackNameTv = itemView.findViewById<TextView>(R.id.track_name)
        private val artistNameTv = itemView.findViewById<TextView>(R.id.artist_name)
        private val trackTimeTv = itemView.findViewById<TextView>(R.id.track_time)
        private val imageTrackIv = itemView.findViewById<ImageView>(R.id.image_track)

        fun bind(track: Track){
            trackNameTv.text = track.trackName
            artistNameTv.text = track.artistName
            trackTimeTv.text = track.trackTime
            Glide.with(itemView)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.search_placeholder)
                .into(imageTrackIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_search, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int = trackList.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(trackList[position])
    }
}