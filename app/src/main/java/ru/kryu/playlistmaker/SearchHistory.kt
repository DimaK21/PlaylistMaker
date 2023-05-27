package ru.kryu.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(val sharedPreferences: SharedPreferences) {

    val listTrackHistory: ArrayList<Track> = getListTrackHistory()

    fun addTrack(track: Track){
        for (element in listTrackHistory){
            if (element.trackId == track.trackId){
                listTrackHistory.remove(element)
                listTrackHistory.add(0, element)
                return
            }
        }
        listTrackHistory.add(0, track)
        if (listTrackHistory.size > 20) listTrackHistory.removeLast()
    }

    private fun getListTrackHistory(): ArrayList<Track>{
        val json = sharedPreferences.getString(SearchActivity.TRACK_HISTORY_KEY, null)
        return if (json != null){
            val myType = object : TypeToken<ArrayList<Track>>() {}.type
            Gson().fromJson(json, myType)
        } else {
            arrayListOf()
        }
    }
}