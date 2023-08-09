package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionSendUseCase

class ActionSendUseCaseImpl(private val actionSendRepository: ActionSendRepository): ActionSendUseCase {
    override fun execute(text: String) {
        actionSendRepository.doActionSend(text = text)
    }
}