package ru.kryu.playlistmaker.search.data.network

import ru.kryu.playlistmaker.search.data.NetworkClient
import ru.kryu.playlistmaker.search.data.dto.ITunesRequest
import ru.kryu.playlistmaker.search.data.dto.ITunesResponse
import ru.kryu.playlistmaker.search.data.dto.Response

class RetrofitNetworkClient(
    private val iTunesApiService: ITunesApiService,
    private val connectionStateProvider: ConnectionStateProvider,
) : NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is ITunesRequest) {
            return Response().apply { resultCode = 400 }
        }
        val response: retrofit2.Response<ITunesResponse>
        return try {
            response = iTunesApiService.search(dto.expression).execute()
            val body = response.body()
            if (body != null) {
                body.apply { resultCode = response.code() }
            } else {
                Response().apply { resultCode = response.code() }
            }
        } catch (e: Exception) {
            Response()
        }
    }

    private fun isConnected(): Boolean {
        return connectionStateProvider.isConnected()
    }
}