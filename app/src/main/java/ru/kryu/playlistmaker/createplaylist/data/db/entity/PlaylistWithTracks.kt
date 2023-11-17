package ru.kryu.playlistmaker.createplaylist.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

data class PlaylistWithTracks(
    @Embedded val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackEntity::class)
    )
    val tracks: List<TrackEntity>
)