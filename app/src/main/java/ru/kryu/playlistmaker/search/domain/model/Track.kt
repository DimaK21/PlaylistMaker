package ru.kryu.playlistmaker.search.domain.model


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
    var isFavorite: Boolean = false,
)