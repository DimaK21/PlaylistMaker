package ru.kryu.playlistmaker.search.data.storage

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.kryu.playlistmaker.search.data.HistoryStorage
import ru.kryu.playlistmaker.search.data.storage.models.TrackForStorage
import ru.kryu.playlistmaker.settings.di.SharedPreferencesSearchDataModule
import javax.inject.Inject

class SharedPrefsHistory @Inject constructor(
    @SharedPreferencesSearchDataModule private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) :
    HistoryStorage {

    override fun getTrackHistory(): List<TrackForStorage> {
        val json = sharedPreferences.getString(TRACK_HISTORY_KEY, null)
        return if (json != null) {
            val myType = object : TypeToken<List<TrackForStorage>>() {}.type
            gson.fromJson(json, myType)
        } else {
            emptyList()
        }
    }

    override fun saveTrackHistory(list: List<TrackForStorage>) {
        val json = gson.toJson(list)
        sharedPreferences.edit()
            .putString(TRACK_HISTORY_KEY, json)
            .apply()
    }

    override fun clearTrackHistory() {
        sharedPreferences.edit()
            .remove(TRACK_HISTORY_KEY)
            .apply()
    }

    companion object {
        const val TRACK_HISTORY_KEY = "track_history_key"
    }
}