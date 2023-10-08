package ru.kryu.playlistmaker.favourite.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kryu.playlistmaker.favourite.domain.FavouritesInteractor
import ru.kryu.playlistmaker.favourite.domain.FavouritesRepository
import ru.kryu.playlistmaker.search.domain.model.Track

class FavouritesInteractorImpl(private val favouritesRepository: FavouritesRepository): FavouritesInteractor {
    override suspend fun addTrack(track: Track) {
        favouritesRepository.addTrack(track)
    }

    override suspend fun removeTrack(track: Track) {
        favouritesRepository.removeTrack(track)
    }

    override fun getTracks(): Flow<List<Track>> {
        val tracks = favouritesRepository.getTracks().map {tracks -> tracks.reversed() }
        return tracks
    }
}