package ru.kryu.playlistmaker.data

import ru.kryu.playlistmaker.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}