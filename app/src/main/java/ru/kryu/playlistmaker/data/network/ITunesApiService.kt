package ru.kryu.playlistmaker.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kryu.playlistmaker.data.dto.ITunesResponse

interface ITunesApiService {

    @GET("search?entity=song")
    fun search(@Query("term") text: String): Call<ITunesResponse>
}