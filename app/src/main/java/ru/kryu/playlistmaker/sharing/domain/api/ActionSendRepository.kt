package ru.kryu.playlistmaker.sharing.domain.api

interface ActionSendRepository {
    fun doActionSend(text: String)
}