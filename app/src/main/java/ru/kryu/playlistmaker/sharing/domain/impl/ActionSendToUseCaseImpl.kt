package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToUseCase

class ActionSendToUseCaseImpl(private val actionSendToRepository: ActionSendToRepository) :
    ActionSendToUseCase {
    override fun execute(email: Array<String>, subject: String, text: String) {
        actionSendToRepository.doActionSendTo(email = email, subject = subject, text = text)
    }
}