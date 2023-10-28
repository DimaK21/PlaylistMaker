package ru.kryu.playlistmaker.createplaylist.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_table")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    var playlistId: Long,
    val playlistName: String,
    val playlistDescription: String,
    val playlistCoverPath: String,
)
