package ru.kryu.playlistmaker.sharing.domain.api

interface ActionSendToRepository {
    fun doActionSendTo(email: Array<String>, subject: String, text: String)
}