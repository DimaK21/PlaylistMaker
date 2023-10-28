package ru.kryu.playlistmaker.favourite.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Dao
interface TrackFavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: TrackEntity)

    @Update
    suspend fun removeTrack(track: TrackEntity)

    @Query("SELECT * FROM track_table WHERE isFavorite = 1 ORDER BY createTime DESC")
    suspend fun getTracks(): List<TrackEntity>

    @Query("SELECT trackId FROM track_table WHERE isFavorite = 1")
    suspend fun getIdTracks(): List<Long>

}