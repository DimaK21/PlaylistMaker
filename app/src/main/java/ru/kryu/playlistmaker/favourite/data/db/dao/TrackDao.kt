package ru.kryu.playlistmaker.favourite.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: TrackEntity)

    @Delete
    suspend fun removeTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table")
    suspend fun getTracks(): List<TrackEntity>

    @Query("SELECT trackId FROM track_table")
    suspend fun getIdTracks(): List<Long>

}