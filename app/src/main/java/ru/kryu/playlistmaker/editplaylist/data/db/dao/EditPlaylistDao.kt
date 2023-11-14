package ru.kryu.playlistmaker.editplaylist.data.db.dao

import androidx.room.Dao
import androidx.room.Update
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistEntity

@Dao
interface EditPlaylistDao {

    @Update
    suspend fun updatePlaylist(playlistEntity: PlaylistEntity)
}