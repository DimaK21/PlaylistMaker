package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository
import ru.kryu.playlistmaker.sharing.domain.api.ActionViewUseCase

class ActionViewUseCaseImpl(private val actionViewRepository: ActionViewRepository) :
    ActionViewUseCase {
    override fun execute(url: String) {
        actionViewRepository.doActionView(url = url)
    }
}