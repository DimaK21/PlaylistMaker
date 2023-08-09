package ru.kryu.playlistmaker.sharing.domain.api

interface ActionViewUseCase {
    fun execute(url: String)
}