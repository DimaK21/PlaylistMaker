package ru.kryu.playlistmaker.favourite.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.favourite.data.mapper.TrackEntityMapper
import ru.kryu.playlistmaker.favourite.domain.FavouritesRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class FavouritesRepositoryImpl(
    private val database: AppDatabase,
    private val trackEntityMapper: TrackEntityMapper
) : FavouritesRepository {

    override suspend fun addTrack(track: Track) {
        database.trackDao().addTrack(trackEntityMapper.map(track))
    }

    override suspend fun removeTrack(track: Track) {
        database.trackDao().removeTrack(trackEntityMapper.map(track))
    }

    override fun getTracks(): Flow<List<Track>> = flow {
        val tracks = database.trackDao().getTracks()
        emit(tracks.map { track -> trackEntityMapper.map(track) })
    }
}