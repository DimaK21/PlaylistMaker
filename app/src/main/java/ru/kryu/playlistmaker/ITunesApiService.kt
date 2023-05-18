package ru.kryu.playlistmaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApiService {

    @GET("search")
    fun search(@Query("term") text: String): Call<ITunesResponce>
}