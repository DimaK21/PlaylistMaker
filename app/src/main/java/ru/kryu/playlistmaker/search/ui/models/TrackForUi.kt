package ru.kryu.playlistmaker.search.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class TrackForUi(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    val artworkUrl512: String,
    var isFavorite: Boolean,
) : Parcelable {

    fun getFormatTrackTimeMillis(): String =
        SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
}