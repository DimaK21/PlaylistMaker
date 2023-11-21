package ru.kryu.playlistmaker.sharing.domain.impl

import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository
import javax.inject.Inject

class ActionViewUseCase @Inject constructor(private val actionViewRepository: ActionViewRepository) {
    fun execute(url: String) {
        actionViewRepository.doActionView(url = url)
    }
}