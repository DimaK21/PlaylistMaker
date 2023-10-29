package ru.kryu.playlistmaker.player.data

import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistEntityMapper
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.favourite.data.mapper.TrackEntityMapper
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository
import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackInPlaylistRepositoryImpl(private val database: AppDatabase) : TrackInPlaylistRepository {
    override suspend fun isTrackInDb(idTrack: Long): Boolean {
        return database.trackInPlaylistDao().existsTrackInDb(idTrack)
    }

    /**
     * Если добавили трек в плейлист, то возвращаем true. Иначе false.
     */
    override suspend fun addTrackInPlaylist(playlist: Playlist, track: Track): Boolean {
        val isTrackInPlaylist =
            database.trackInPlaylistDao()
                .existsTrackInPlaylist(playlist.playlistId!!, track.trackId)
        return if (isTrackInPlaylist) {
            false
        } else {
            database.trackInPlaylistDao().addTrackInPlaylist(
                playlist = PlaylistEntityMapper.map(playlist),
                track = TrackEntityMapper.map(track)
            )
            return true
        }
    }

}