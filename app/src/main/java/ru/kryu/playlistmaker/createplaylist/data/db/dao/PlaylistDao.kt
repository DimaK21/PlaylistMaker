package ru.kryu.playlistmaker.createplaylist.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks
import ru.kryu.playlistmaker.createplaylist.data.db.entity.TrackWithPlaylists

@Dao
interface PlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlist_table")
    fun getPlaylistsWithSongs(): List<PlaylistWithTracks>

    @Transaction
    @Query("SELECT * FROM track_table")
    fun getSongsWithPlaylists(): List<TrackWithPlaylists>
}