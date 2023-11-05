package ru.kryu.playlistmaker.player.ui.viewmodel

sealed interface TrackInPlaylistState{
    data class TrackInPlaylistAdded(val playlistName: String): TrackInPlaylistState
    data class TrackInPlaylistNotAdded(val playlistName: String): TrackInPlaylistState
}