package ru.kryu.playlistmaker.playlistmain.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

@Parcelize
data class PlaylistMainItem(
    val playlistId: Long,
    var playlistName: String,
    var playlistDescription: String,
    var playlistCoverPath: String,
    var countTracks: Int,
    var playlistDuration: Long,
    var tracks: List<TrackForUi>
) : Parcelable
