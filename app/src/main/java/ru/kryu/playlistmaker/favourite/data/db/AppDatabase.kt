package ru.kryu.playlistmaker.favourite.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kryu.playlistmaker.createplaylist.data.db.dao.PlaylistDao
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.favourite.data.db.dao.TrackDao
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Database(
    version = 3, entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
}