package ru.kryu.playlistmaker.createplaylist.data.db.entity

import androidx.room.Entity

@Entity(
    tableName = "playlist_track_table",
    primaryKeys = ["playlistId", "trackId"]
)
data class PlaylistTrackEntity(
    val playlistId: Long,
    val trackId: Long,
)
