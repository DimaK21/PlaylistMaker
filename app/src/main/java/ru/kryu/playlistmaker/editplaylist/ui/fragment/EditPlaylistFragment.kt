package ru.kryu.playlistmaker.editplaylist.ui.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.kryu.playlistmaker.createplaylist.ui.fragment.CreatePlaylistFragment
import ru.kryu.playlistmaker.playlistmain.ui.model.PlaylistMainItem

class EditPlaylistFragment : CreatePlaylistFragment() {

    companion object {
        private const val PLAYLIST = "PLAYLIST"
        fun createArgs(playlist: PlaylistMainItem): Bundle =
            bundleOf(EditPlaylistFragment.PLAYLIST to playlist)
    }
}