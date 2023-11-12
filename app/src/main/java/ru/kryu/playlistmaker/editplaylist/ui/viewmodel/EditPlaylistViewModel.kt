package ru.kryu.playlistmaker.editplaylist.ui.viewmodel

import ru.kryu.playlistmaker.createplaylist.domain.api.CreatePlaylistInteractor
import ru.kryu.playlistmaker.createplaylist.ui.viewmodel.CreatePlaylistViewModel

class EditPlaylistViewModel(
    createPlaylistInteractor: CreatePlaylistInteractor
) : CreatePlaylistViewModel(createPlaylistInteractor) {

}