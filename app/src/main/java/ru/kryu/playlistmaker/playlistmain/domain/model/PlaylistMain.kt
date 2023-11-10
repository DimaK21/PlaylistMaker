package ru.kryu.playlistmaker.playlistmain.domain.model

import ru.kryu.playlistmaker.playlists.domain.model.Playlist
import ru.kryu.playlistmaker.search.domain.model.Track

data class PlaylistMain(
    val playlist: Playlist,
    val tracks: List<Track>
)
