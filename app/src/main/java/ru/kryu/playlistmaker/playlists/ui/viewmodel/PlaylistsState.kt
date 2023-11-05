package ru.kryu.playlistmaker.playlists.ui.viewmodel

import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

sealed interface PlaylistsState {
    object Empty : PlaylistsState
    data class Content(val playlists: List<PlaylistItemUi>) : PlaylistsState
}