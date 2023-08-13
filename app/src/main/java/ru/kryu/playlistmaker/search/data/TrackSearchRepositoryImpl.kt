package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.dto.ITunesRequest
import ru.kryu.playlistmaker.search.data.dto.ITunesResponse
import ru.kryu.playlistmaker.search.data.mapper.TrackDtoToDomain
import ru.kryu.playlistmaker.search.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.model.Resource
import ru.kryu.playlistmaker.search.domain.model.Track

class TrackSearchRepositoryImpl(private val networkClient: NetworkClient) : TrackSearchRepository {

    override fun searchTracks(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(ITunesRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Success((response as ITunesResponse).results.map {
                    TrackDtoToDomain().map(it)
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}