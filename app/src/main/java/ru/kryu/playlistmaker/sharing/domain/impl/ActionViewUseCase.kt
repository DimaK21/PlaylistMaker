package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository

class ActionViewUseCase(private val actionViewRepository: ActionViewRepository) {
    fun execute(url: String) {
        actionViewRepository.doActionView(url = url)
    }
}