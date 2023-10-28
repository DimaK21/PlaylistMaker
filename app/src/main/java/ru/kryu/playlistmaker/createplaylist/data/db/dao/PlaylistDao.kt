package ru.kryu.playlistmaker.createplaylist.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks
import ru.kryu.playlistmaker.createplaylist.data.db.entity.TrackWithPlaylists
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity
import ru.kryu.playlistmaker.playlist.domain.model.Playlist

@Dao
interface PlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlist_table")
    fun getPlaylistsWithTracks(): List<PlaylistWithTracks>

    @Transaction
    @Query("SELECT * FROM track_table")
    fun getTracksWithPlaylists(): List<TrackWithPlaylists>

    @Transaction
    @Query("SELECT * FROM playlist_table WHERE playlistId = :playlistId")
    fun getPlaylistWithTracks(playlistId: Long): PlaylistWithTracks?

    @Transaction
    @Query("SELECT * FROM track_table WHERE trackId = :trackId")
    fun getTrackWithPlaylists(trackId: Long): TrackWithPlaylists?

    @Query("SELECT count(*) FROM playlist_track_table WHERE playlistId = :playlistId")
    fun getCountTracksInPlaylist(playlistId: Long): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlaylist(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTrackInPlaylist(playlistTrackEntity: PlaylistTrackEntity)

    @Delete
    fun removeTrackFromPlaylist(playlistTrackEntity: PlaylistTrackEntity)

    @Delete
    fun removePlaylist(playlistEntity: PlaylistEntity)
}