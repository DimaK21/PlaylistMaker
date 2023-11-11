package ru.kryu.playlistmaker.playlists.ui.models

data class PlaylistItemUi(
    val playlistId: Long,
    val playlistImage: String,
    val playlistName: String,
    val playlistTracksNumber: Int
) {
    fun getPlaylistTracksNumberText(): String {
        var lastNumbers = playlistTracksNumber % 100
        return if (lastNumbers in 11..14) {
            "$playlistTracksNumber треков"
        } else {
            lastNumbers = playlistTracksNumber % 10
            when (lastNumbers) {
                1 -> "$playlistTracksNumber трек"
                in 2..4 -> "$playlistTracksNumber трека"
                else -> "$playlistTracksNumber треков"
            }
        }
    }
}
