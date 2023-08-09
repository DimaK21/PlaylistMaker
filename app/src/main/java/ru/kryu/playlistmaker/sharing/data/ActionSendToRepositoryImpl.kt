package ru.kryu.playlistmaker.sharing.data

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendToRepository

class ActionSendToRepositoryImpl(private val actionSendTo: ActionSendTo): ActionSendToRepository {
    override fun doActionSendTo(email: Array<String>, subject: String, text: String) {
        actionSendTo.share(email, subject, text)
    }
}