package ru.kryu.playlistmaker.sharing.data

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository

class ActionSendRepositoryImpl(private val actionSend: ActionSend): ActionSendRepository {
    override fun doActionSend(text: String) {
        actionSend.share(text = text)
    }
}