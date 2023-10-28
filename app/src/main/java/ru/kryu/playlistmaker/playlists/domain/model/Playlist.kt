package ru.kryu.playlistmaker.playlists.domain.model

data class Playlist(
    val playlistId: Long? = null,
    val playlistName: String,
    val playlistDescription: String,
    val playlistCoverPath: String,
)