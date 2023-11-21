package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import javax.inject.Inject

class ActionSendUseCase @Inject constructor(private val actionSendRepository: ActionSendRepository) {
    fun execute(text: String) {
        actionSendRepository.doActionSend(text = text)
    }
}