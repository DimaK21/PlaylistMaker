package ru.kryu.playlistmaker.player.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity

@Dao
interface TrackInPlaylistDao {
    @Query("SELECT EXISTS(SELECT * FROM track_table WHERE trackId=:idTrack)")
    suspend fun existsTrackInDb(idTrack: Long): Boolean

    @Query("SELECT EXISTS(SELECT * FROM playlist_track_table WHERE playlistId=:playlistId AND trackId=:trackId)")
    suspend fun existsTrackInPlaylist(playlistId: Long, trackId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrackInPlaylist(playlistTrackEntity: PlaylistTrackEntity)
}