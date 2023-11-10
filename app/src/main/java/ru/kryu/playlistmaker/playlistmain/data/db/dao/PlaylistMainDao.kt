package ru.kryu.playlistmaker.playlistmain.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks
import ru.kryu.playlistmaker.createplaylist.data.db.entity.TrackWithPlaylists

@Dao
interface PlaylistMainDao {
    @Transaction
    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    fun getPlaylistWithTracks(playlistId: Long): Flow<PlaylistWithTracks>

//    @Transaction
//    @Query("SELECT * FROM track_table")
//    suspend fun getTracksWithPlaylists(): List<TrackWithPlaylists>
}