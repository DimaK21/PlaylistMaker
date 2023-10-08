package ru.kryu.playlistmaker.favourite.domain

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.search.domain.model.Track

interface FavouritesRepository {

    suspend fun addTrack(track: Track)
    suspend fun removeTrack(track: Track)
    fun getTracks(): Flow<List<Track>>
}