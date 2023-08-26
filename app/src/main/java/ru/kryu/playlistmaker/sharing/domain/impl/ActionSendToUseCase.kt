package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository

class ActionSendToUseCase(private val actionSendToRepository: ActionSendToRepository) {
    fun execute(email: Array<String>, subject: String, text: String) {
        actionSendToRepository.doActionSendTo(email = email, subject = subject, text = text)
    }
}