package ru.kryu.playlistmaker.search.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.kryu.playlistmaker.favourite.data.db.AppDatabase
import ru.kryu.playlistmaker.search.data.dto.ITunesRequest
import ru.kryu.playlistmaker.search.data.dto.ITunesResponse
import ru.kryu.playlistmaker.search.data.mapper.TrackDtoToDomain
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.model.Resource
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackSearchRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: AppDatabase
) : TrackSearchRepository {

    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(ITunesRequest(expression))
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Проверьте подключение к интернету"))
            }

            200 -> {
                val resource = Resource.Success((response as ITunesResponse).results.map {
                    TrackDtoToDomain.map(it)
                })
                val favouritesTracks = withContext(Dispatchers.IO) {
                    database.trackFavouriteDao().getIdTracks()
                }
                resource.data?.map { track ->
                    if (favouritesTracks.contains(track.trackId)) {
                        track.isFavorite = true
                    }
                }
                emit(resource)
            }

            400 -> {
                emit(Resource.Error("Ошибка в запросе"))
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }
}