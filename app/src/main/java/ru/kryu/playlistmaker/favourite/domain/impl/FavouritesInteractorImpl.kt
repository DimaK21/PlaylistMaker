package ru.kryu.playlistmaker.favourite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesInteractor
import ru.kryu.playlistmaker.favourite.domain.api.FavouritesRepository
import ru.kryu.playlistmaker.search.domain.model.Track
import javax.inject.Inject

class FavouritesInteractorImpl @Inject constructor(private val favouritesRepository: FavouritesRepository) :
    FavouritesInteractor {
    override suspend fun addTrack(track: Track) {
        favouritesRepository.addTrack(track)
    }

    override suspend fun removeTrack(track: Track) {
        favouritesRepository.removeTrack(track)
    }

    override fun getTracks(): Flow<List<Track>> {
        return favouritesRepository.getTracks()
    }
}