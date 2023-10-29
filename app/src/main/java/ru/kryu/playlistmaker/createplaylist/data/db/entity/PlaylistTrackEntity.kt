package ru.kryu.playlistmaker.createplaylist.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Entity(tableName = "playlist_track_table",
    primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrackEntity(
    val playlistId: Long,
    val trackId: Long,
)
