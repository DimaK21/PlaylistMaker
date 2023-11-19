package ru.kryu.playlistmaker.playlists.ui.models

data class PlaylistItemUi(
    val playlistId: Long,
    val playlistImage: String,
    val playlistName: String,
    val playlistTracksNumber: Int
)
