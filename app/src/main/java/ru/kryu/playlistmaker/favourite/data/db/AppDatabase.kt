package ru.kryu.playlistmaker.favourite.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kryu.playlistmaker.createplaylist.data.db.dao.PlaylistDao
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistTrackEntity
import ru.kryu.playlistmaker.editplaylist.data.db.dao.EditPlaylistDao
import ru.kryu.playlistmaker.favourite.data.db.dao.TrackFavouriteDao
import ru.kryu.playlistmaker.favourite.data.db.entity.TrackEntity
import ru.kryu.playlistmaker.player.data.db.dao.TrackInPlaylistDao
import ru.kryu.playlistmaker.playlistmain.data.db.dao.PlaylistMainDao

@Database(
    version = 1, entities = [TrackEntity::class, PlaylistEntity::class, PlaylistTrackEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackFavouriteDao(): TrackFavouriteDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun trackInPlaylistDao(): TrackInPlaylistDao
    abstract fun playlistMainDao(): PlaylistMainDao
    abstract fun editPlaylistDao(): EditPlaylistDao
}