package ru.kryu.playlistmaker.sharing.domain.api

interface ActionSendUseCase {
    fun execute(text: String)
}