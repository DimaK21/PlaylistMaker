package ru.kryu.playlistmaker.player.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Dao
interface TrackInPlaylistDao {
    @Query("SELECT EXISTS(SELECT * FROM track_table WHERE trackId=:idTrack)")
    suspend fun existsTrackInDb(idTrack: Long): Boolean

    @Query("SELECT EXISTS(SELECT * FROM playlist_track_table WHERE playlistId=:playlistId AND trackId=:trackId)")
    suspend fun existsTrackInPlaylist(playlistId: Long, trackId: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = PlaylistTrackEntity::class)
    suspend fun addTrackInPlaylist(playlist: PlaylistEntity, track: TrackEntity)
}