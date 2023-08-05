package ru.kryu.playlistmaker.search.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kryu.playlistmaker.search.data.NetworkClient
import ru.kryu.playlistmaker.search.data.dto.ITunesRequest
import ru.kryu.playlistmaker.search.data.dto.Response

class RetrofitNetworkClient: NetworkClient {

    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesApiService = retrofit.create(ITunesApiService::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is ITunesRequest){
            val response = iTunesApiService.search(dto.expression).execute()
            val body = response.body() ?: Response()
            return body.apply { resultCode = response.code() }
        }else{
            return Response().apply { resultCode = 400 }
        }
    }
}