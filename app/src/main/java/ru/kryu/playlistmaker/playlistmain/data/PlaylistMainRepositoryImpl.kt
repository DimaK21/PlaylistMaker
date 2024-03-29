package ru.kryu.playlistmaker.playlistmain.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import ru.kryu.playlistmaker.createplaylist.data.db.entity.PlaylistWithTracks
import ru.kryu.playlistmaker.createplaylist.data.mapper.PlaylistWithTracksMapper
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.playlistmain.domain.api.PlaylistMainRepository
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain

class PlaylistMainRepositoryImpl(private val database: AppDatabase) : PlaylistMainRepository {
    override fun getPlaylistMain(playlistId: Long): Flow<PlaylistMain> {
        return database.playlistMainDao()
            .getPlaylist(playlistId)
            .flatMapMerge { playlist ->
                database.playlistMainDao().getTracksFromPlaylist(playlistId)
                    .map { tracks ->
                        PlaylistWithTracksMapper.map(
                            PlaylistWithTracks(
                                playlist = playlist,
                                tracks = tracks
                            )
                        )
                    }
            }
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: Long) {
        database.playlistMainDao().removeTrackFromPlaylist(playlistId, trackId)
        if (!database.playlistMainDao().isTrackInPlaylists(trackId) &&
            !database.playlistMainDao().isTrackIsFavorite(trackId)
        ) {
            database.playlistMainDao().removeTrackFromDb(trackId)
        }
    }

    override suspend fun deletePlaylist(playlistId: Long) {
        database.playlistMainDao().deletePlaylist(playlistId)
    }
}