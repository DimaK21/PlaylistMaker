package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.dto.ITunesRequest
import ru.kryu.playlistmaker.search.data.dto.ITunesResponse
import ru.kryu.playlistmaker.search.data.mapper.TrackDtoToDomain
import ru.kryu.playlistmaker.search.domain.TrackSearchRepository
import ru.kryu.playlistmaker.search.domain.Track

class TrackSearchRepositoryImpl(private val networkClient: NetworkClient): TrackSearchRepository {

    override fun searchTracks(expression: String): List<Track>? {
        val response = networkClient.doRequest(ITunesRequest(expression))
        if (response.resultCode == 200){
            return (response as ITunesResponse).results.map { TrackDtoToDomain().trackDataToDomain(it) }
        }else{
            return null
        }
    }
}