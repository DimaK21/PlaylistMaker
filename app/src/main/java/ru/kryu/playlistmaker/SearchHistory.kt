package ru.kryu.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(val sharedPreferences: SharedPreferences) {

    private val arrayListTrackHistory: ArrayList<Track> = getTrackHistory()
    val listTrackHistory: List<Track> = arrayListTrackHistory

    fun addTrack(track: Track) {
        for (element in arrayListTrackHistory) {
            if (element.trackId == track.trackId) {
                arrayListTrackHistory.remove(element)
                arrayListTrackHistory.add(0, element)
                return
            }
        }
        arrayListTrackHistory.add(0, track)
        if (arrayListTrackHistory.size > 10) arrayListTrackHistory.removeLast()
    }

    private fun getTrackHistory(): ArrayList<Track> {
        val json = sharedPreferences.getString(SearchActivity.TRACK_HISTORY_KEY, null)
        return if (json != null) {
            val myType = object : TypeToken<ArrayList<Track>>() {}.type
            Gson().fromJson(json, myType)
        } else {
            arrayListOf()
        }
    }

    fun saveTrackHistory() {
        val json = Gson().toJson(arrayListTrackHistory)
        sharedPreferences.edit()
            .putString(SearchActivity.TRACK_HISTORY_KEY, json)
            .apply()
    }

    fun clearTrackHistory() {
        arrayListTrackHistory.clear()
        sharedPreferences.edit()
            .remove(SearchActivity.TRACK_HISTORY_KEY)
            .apply()
    }
}