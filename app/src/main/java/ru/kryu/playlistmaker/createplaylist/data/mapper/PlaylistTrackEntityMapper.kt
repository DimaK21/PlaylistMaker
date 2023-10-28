package ru.kryu.playlistmaker.createplaylist.data.mapper

import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.search.domain.model.Track

object PlaylistTrackEntityMapper {
    fun map(playlist: PlaylistEntity, track: TrackEntity): PlaylistTrackEntity {
        return PlaylistTrackEntity(playlistId = playlist.playlistId!!, trackId = track.trackId)
    }

    fun map(playlist: Playlist, track: Track): PlaylistTrackEntity {
        return PlaylistTrackEntity(playlistId = playlist.playlistId!!, trackId = track.trackId)
    }
}