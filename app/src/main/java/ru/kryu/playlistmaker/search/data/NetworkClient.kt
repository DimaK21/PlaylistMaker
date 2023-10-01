package ru.kryu.playlistmaker.search.data

import ru.kryu.playlistmaker.search.data.dto.Response

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}