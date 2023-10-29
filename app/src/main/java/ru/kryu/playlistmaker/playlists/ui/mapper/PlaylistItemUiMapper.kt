package ru.kryu.playlistmaker.playlists.ui.mapper

import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi

object PlaylistItemUiMapper {
    fun map(playlist: Playlist): PlaylistItemUi{
        return PlaylistItemUi(
            playlistId = playlist.playlistId!!,
            playlistImage = playlist.playlistCoverPath,
            playlistName = playlist.playlistName,
            playlistTracksNumber = playlist.countTracks,
        )
    }

    fun map(playlist: PlaylistItemUi): Playlist{
        return Playlist(
            playlistId = playlist.playlistId,
            playlistCoverPath = playlist.playlistImage,
            playlistName = playlist.playlistName,
            countTracks = playlist.playlistTracksNumber,
            playlistDescription = "",
        )
    }
}