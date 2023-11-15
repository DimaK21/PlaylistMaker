package ru.kryu.playlistmaker.createplaylist.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Junction
import androidx.room.Relation
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

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

data class PlaylistWithTracks(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackEntity::class)
    )
    val tracks: List<TrackEntity>
)
