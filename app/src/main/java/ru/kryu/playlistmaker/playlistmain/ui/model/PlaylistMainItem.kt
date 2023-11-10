package ru.kryu.playlistmaker.playlistmain.ui.model

import ru.kryu.playlistmaker.search.ui.models.TrackForUi

data class PlaylistMainItem(
    val playlistId: Long,
    var playlistName: String,
    var playlistDescription: String,
    var playlistCoverPath: String,
    var countTracks: Int,
    var playlistDuration: Long,
    var tracks: List<TrackForUi>
)
