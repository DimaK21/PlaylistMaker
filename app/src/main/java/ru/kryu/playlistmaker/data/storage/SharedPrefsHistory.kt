package ru.kryu.playlistmaker.data.storage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.kryu.playlistmaker.data.HistoryStorage
import ru.kryu.playlistmaker.data.storage.models.TrackForStorage

class SharedPrefsHistory(context: Context) : HistoryStorage {

    val sharedPreferences = context.getSharedPreferences(
        TRACK_HISTORY_PREFERENCES,
        AppCompatActivity.MODE_PRIVATE
    )

    override fun getTrackHistory(): List<TrackForStorage> {
        val json = sharedPreferences.getString(TRACK_HISTORY_KEY, null)
        return if (json != null) {
            val myType = object : TypeToken<List<TrackForStorage>>() {}.type
            Gson().fromJson(json, myType)
        } else {
            emptyList()
        }
    }

    override fun saveTrackHistory(list: List<TrackForStorage>) {
        val json = Gson().toJson(list)
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
        const val TRACK_HISTORY_PREFERENCES = "track_history_preferences"
        const val TRACK_HISTORY_KEY = "track_history_key"
    }
}