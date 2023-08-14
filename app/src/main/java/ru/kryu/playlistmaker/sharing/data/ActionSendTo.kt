package ru.kryu.playlistmaker.sharing.data

interface ActionSendTo {
    fun share(email: Array<String>, subject: String, text: String)
}