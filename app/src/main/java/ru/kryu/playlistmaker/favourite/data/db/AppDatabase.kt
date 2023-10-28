package ru.kryu.playlistmaker.favourite.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kryu.playlistmaker.createplaylist.data.db.dao.PlaylistDao
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.favourite.data.db.dao.TrackFavouriteDao
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Database(
    version = 4, entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackFavouriteDao(): TrackFavouriteDao
    abstract fun playlistDao(): PlaylistDao
}