package ru.kryu.playlistmaker.createplaylist.data.mapper

import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

object PlaylistEntityMapper {
    fun map(playlist: PlaylistEntity): Playlist {
        return Playlist(
            playlistId = playlist.playlistId,
            playlistName = playlist.playlistName,
            playlistDescription = playlist.playlistDescription,
            playlistCoverPath = playlist.playlistCoverPath,
        )
    }

    fun map(playlist: Playlist): PlaylistEntity {
        return PlaylistEntity(
            playlistId = playlist.playlistId,
            playlistName = playlist.playlistName,
            playlistDescription = playlist.playlistDescription,
            playlistCoverPath = playlist.playlistCoverPath,
        )
    }
}