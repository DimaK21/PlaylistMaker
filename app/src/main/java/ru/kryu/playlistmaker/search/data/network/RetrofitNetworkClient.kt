package ru.kryu.playlistmaker.search.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kryu.playlistmaker.search.data.NetworkClient
import ru.kryu.playlistmaker.search.data.dto.ITunesRequest
import ru.kryu.playlistmaker.search.data.dto.Response
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val iTunesApiService: ITunesApiService,
    private val connectionStateProvider: ConnectionStateProvider,
) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if (dto !is ITunesRequest) {
            return Response().apply { resultCode = 400 }
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = iTunesApiService.search(dto.expression)
                response.apply { resultCode = 200 }
            } catch (e: Throwable) {
                Response().apply { resultCode = 500 }
            }
        }
    }

    private fun isConnected(): Boolean {
        return connectionStateProvider.isConnected()
    }
}