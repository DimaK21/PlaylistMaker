package ru.kryu.playlistmaker.sharing.domain.api

interface ActionSendToUseCase {
    fun execute(email: Array<String>, subject: String, text: String)
}