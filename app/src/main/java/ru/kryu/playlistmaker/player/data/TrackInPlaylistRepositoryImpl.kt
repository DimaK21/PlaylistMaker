package ru.kryu.playlistmaker.player.data

import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.player.domain.api.TrackInPlaylistRepository

class TrackInPlaylistRepositoryImpl(private val database: AppDatabase) : TrackInPlaylistRepository {
    override suspend fun isTrackInDb(idTrack: Long): Boolean {
        return database.trackInPlaylistDao().existsTrackInDb(idTrack)
    }

    /**
     * Если добавили трек в плейлист, то возвращаем true. Иначе false.
     */
    override suspend fun addTrackInPlaylist(playlistId: Long, trackId: Long): Boolean {
        val isTrackInPlaylist =
            database.trackInPlaylistDao()
                .existsTrackInPlaylist(playlistId, trackId)
        return if (isTrackInPlaylist) {
            false
        } else {
            database.trackInPlaylistDao().addTrackInPlaylist(
                PlaylistTrackEntity(
                    playlistId = playlistId,
                    trackId = trackId,
                    createTime = System.currentTimeMillis(),
                )
            )
            return true
        }
    }

}