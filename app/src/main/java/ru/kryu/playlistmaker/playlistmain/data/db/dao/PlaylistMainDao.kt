package ru.kryu.playlistmaker.playlistmain.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Dao
interface PlaylistMainDao {
    @Transaction
    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    fun getPlaylistWithTracks(playlistId: Long): Flow<PlaylistWithTracks>

    @Query("SELECT * FROM track_table JOIN playlist_track_table ON track_table.trackId = playlist_track_table.trackId WHERE playlist_track_table.playlistId = :playlistId ORDER BY playlist_track_table.createTime DESC")
    fun getTracksFromPlaylist(playlistId: Long): Flow<List<TrackEntity>>

    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    fun getPlaylist(playlistId: Long): Flow<PlaylistEntity>

    @Query("DELETE FROM playlist_track_table WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Query("DELETE FROM track_table WHERE trackId = :trackId")
    suspend fun removeTrackFromDb(trackId: Long)

    @Query("SELECT EXISTS(SELECT * FROM playlist_track_table WHERE trackId = :trackId)")
    suspend fun isTrackInPlaylists(trackId: Long): Boolean

    @Query("SELECT isFavorite FROM track_table WHERE trackId = :trackId")
    suspend fun isTrackIsFavorite(trackId: Long): Boolean

    @Query("DELETE FROM playlist_table WHERE playlistId = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)
}