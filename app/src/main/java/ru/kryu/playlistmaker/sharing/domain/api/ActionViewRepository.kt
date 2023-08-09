package ru.kryu.playlistmaker.sharing.domain.api

interface ActionViewRepository {
    fun doActionView(url: String)
}