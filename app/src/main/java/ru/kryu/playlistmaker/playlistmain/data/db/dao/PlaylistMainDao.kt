package ru.kryu.playlistmaker.playlistmain.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks

@Dao
interface PlaylistMainDao {
    @Transaction
    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    fun getPlaylistWithTracks(playlistId: Long): Flow<PlaylistWithTracks>

    @Query("DELETE FROM playlist_track_table WHERE playlistId = :playlistId AND trackId = :trackId")
    fun removeTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Query("DELETE FROM track_table WHERE trackId = :trackId")
    suspend fun removeTrackFromDb(trackId: Long)

    @Query("SELECT EXISTS(SELECT * FROM playlist_track_table WHERE trackId = :trackId)")
    fun isTrackInPlaylists(trackId: Long): Boolean

    @Query("SELECT isFavorite FROM track_table WHERE trackId = :trackId")
    fun isTrackIsFavorite(trackId: Long): Boolean
}