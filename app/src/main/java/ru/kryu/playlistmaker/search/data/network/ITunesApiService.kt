package ru.kryu.playlistmaker.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kryu.playlistmaker.search.data.dto.ITunesResponse

interface ITunesApiService {

    @GET("search?entity=song")
    suspend fun search(@Query("term") text: String): ITunesResponse
}