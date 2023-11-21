package ru.kryu.playlistmaker.sharing.data

import ru.kryu.playlistmaker.sharing.domain.api.ActionViewRepository
import javax.inject.Inject

class ActionViewRepositoryImpl @Inject constructor(private val actionView: ActionView) : ActionViewRepository {
    override fun doActionView(url: String) {
        actionView.share(url)
    }
}