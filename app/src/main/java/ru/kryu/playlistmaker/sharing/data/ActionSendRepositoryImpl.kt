package ru.kryu.playlistmaker.sharing.data

import ru.kryu.playlistmaker.sharing.domain.api.ActionSendRepository
import javax.inject.Inject

class ActionSendRepositoryImpl @Inject constructor(private val actionSend: ActionSend) : ActionSendRepository {
    override fun doActionSend(text: String) {
        actionSend.share(text = text)
    }
}