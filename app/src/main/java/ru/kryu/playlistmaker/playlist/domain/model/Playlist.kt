package ru.kryu.playlistmaker.playlist.domain.model

data class Playlist(
    val playlistId: Long,
    val playlistName: String,
    val playlistDescription: String,
    val playlistCoverPath: String,
)