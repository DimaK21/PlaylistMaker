package ru.kryu.playlistmaker.search.di

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kryu.playlistmaker.search.data.HistoryStorage
import ru.kryu.playlistmaker.search.data.NetworkClient
import ru.kryu.playlistmaker.search.data.network.ITunesApiService
import ru.kryu.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.search.data.storage.SharedPrefsHistory

const val ITUNES_BASE_URL = "https://itunes.apple.com"
const val TRACK_HISTORY_PREFERENCES = "track_history_preferences"

val searchDataModule = module {
    single<NetworkClient> {
        RetrofitNetworkClient(iTunesApiService = get(), networkCapabilities = get())
    }
    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl(ITUNES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }
    single<NetworkCapabilities?> {
        val connectivityManager = androidContext().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    }
    single<HistoryStorage> {
        SharedPrefsHistory(sharedPreferences = get(), gson = get())
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            TRACK_HISTORY_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )
    }
    factory<Gson> {
        Gson()
    }
}