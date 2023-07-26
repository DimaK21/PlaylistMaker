package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.data.dto.ITunesRequest
import ru.kryu.playlistmaker.data.dto.ITunesResponse
import ru.kryu.playlistmaker.data.mapper.TrackDataToDomain
import ru.kryu.playlistmaker.domain.api.TrackSearchRepository
import ru.kryu.playlistmaker.domain.models.Track

class TrackSearchRepositoryImpl(private val networkClient: NetworkClient): TrackSearchRepository {

    override fun searchTracks(expression: String): List<Track>? {
        val response = networkClient.doRequest(ITunesRequest(expression))
        if (response.resultCode == 200){
            return (response as ITunesResponse).results.map { TrackDataToDomain().trackDataToDomain(it) }
        }else{
            return null
        }
    }
}