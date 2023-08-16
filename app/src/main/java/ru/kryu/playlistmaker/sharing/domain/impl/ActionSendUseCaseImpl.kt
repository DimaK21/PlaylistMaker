package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository

class ActionSendUseCaseImpl(private val actionSendRepository: ActionSendRepository) {
    fun execute(text: String) {
        actionSendRepository.doActionSend(text = text)
    }
}