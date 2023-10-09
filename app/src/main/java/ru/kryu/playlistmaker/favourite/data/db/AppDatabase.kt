package ru.kryu.playlistmaker.favourite.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kryu.playlistmaker.favourite.data.db.dao.TrackDao
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity

@Database(
    version = 2, entities = [TrackEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}