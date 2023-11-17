package ru.kryu.playlistmaker.createplaylist.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "playlist_track_table",
    primaryKeys = ["playlistId", "trackId"],
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = PlaylistEntity::class,
            parentColumns = ["playlistId"],
            childColumns = ["playlistId"]
        ),
    ]
)
data class PlaylistTrackEntity(
    val playlistId: Long,
    val trackId: Long,
    val createTime: Long,
)
