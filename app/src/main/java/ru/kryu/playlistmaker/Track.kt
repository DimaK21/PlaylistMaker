package ru.kryu.playlistmaker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Locale

@Parcelize
data class Track(
    val trackId: Long = 0, //id трека
    val trackName: String = "", // Название композиции
    val artistName: String = "", // Имя исполнителя
    val trackTimeMillis: Long = 0, // Продолжительность трека
    val artworkUrl100: String = "", // Ссылка на изображение обложки
    val collectionName: String = "", // Название альбома
    val releaseDate: String = "", // Год релиза трека
    val primaryGenreName: String = "", // Жанр трека
    val country: String = "", // Страна исполнителя
    val previewUrl: String = "", //Потоковое аудио
) : Parcelable {
    val artworkUrl512: String
        get() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

    fun getFormatTrackTimeMillis(): String =
        SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
}