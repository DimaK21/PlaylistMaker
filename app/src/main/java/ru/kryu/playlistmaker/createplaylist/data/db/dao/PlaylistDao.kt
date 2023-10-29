package ru.kryu.playlistmaker.createplaylist.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity

@Dao
interface PlaylistDao {

    @Query("SELECT count(*) FROM playlist_track_table WHERE playlistId = :playlistId")
    fun getCountTracksInPlaylist(playlistId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlaylist(playlist: PlaylistEntity)

    @Query("SELECT * FROM playlist_table")
    fun getPlaylists(): List<PlaylistEntity>
}