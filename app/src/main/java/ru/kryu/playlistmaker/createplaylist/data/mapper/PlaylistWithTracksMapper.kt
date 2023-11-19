package ru.kryu.playlistmaker.createplaylist.data.mapper

import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks
import ru.kryu.playlistmaker.favourite.data.mapper.TrackEntityMapper
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain
import ru.kryu.playlistmaker.playlists.domain.model.Playlist

object PlaylistWithTracksMapper {

    fun map(playlistWithTracks: PlaylistWithTracks): PlaylistMain {
        return PlaylistMain(
            playlist = Playlist(
                playlistId = playlistWithTracks.playlist.playlistId,
                playlistName = playlistWithTracks.playlist.playlistName,
                playlistDescription = playlistWithTracks.playlist.playlistDescription,
                playlistCoverPath = playlistWithTracks.playlist.playlistCoverPath,
                countTracks = playlistWithTracks.tracks.size
            ),
            tracks = playlistWithTracks.tracks.map { trackEntity ->
                TrackEntityMapper.map(trackEntity)
            }
        )
    }
}