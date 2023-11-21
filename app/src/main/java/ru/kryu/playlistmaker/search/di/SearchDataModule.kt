package ru.kryu.playlistmaker.search.di

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kryu.playlistmaker.search.data.HistoryStorage
import ru.kryu.playlistmaker.search.data.NetworkClient
import ru.kryu.playlistmaker.search.data.network.ConnectionStateProvider
import ru.kryu.playlistmaker.search.data.network.ITunesApiService
import ru.kryu.playlistmaker.search.data.network.RetrofitNetworkClient
import ru.kryu.playlistmaker.search.data.storage.SharedPrefsHistory
import ru.kryu.playlistmaker.settings.di.SharedPreferencesSearchDataModule
import javax.inject.Singleton

const val ITUNES_BASE_URL = "https://itunes.apple.com/"
const val TRACK_HISTORY_PREFERENCES = "track_history_preferences"

@Module
@InstallIn(SingletonComponent::class)
class SearchDataModule {
    @Provides
    fun provideNetworkClient(
        iTunesApiService: ITunesApiService,
        connectionStateProvider: ConnectionStateProvider
    ): NetworkClient =
        RetrofitNetworkClient(
            iTunesApiService = iTunesApiService,
            connectionStateProvider = connectionStateProvider
        )

    @Provides
    @Singleton
    fun provideITunesApiService(): ITunesApiService =
        Retrofit.Builder()
            .baseUrl(ITUNES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)

    @Provides
    fun provideConnectionStateProvider(
        connectivityManager: ConnectivityManager
    ): ConnectionStateProvider =
        ConnectionStateProvider(connectivityManager = connectivityManager)

    @Provides
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    @SharedPreferencesSearchDataModule
    @Provides
    fun provideHistoryStorage(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): HistoryStorage =
        SharedPrefsHistory(sharedPreferences = sharedPreferences, gson = gson)

    @Provides
    fun provideHistoryStorage2(
        sharedPrefsHistory: SharedPrefsHistory,
    ): HistoryStorage =
        sharedPrefsHistory

    @SharedPreferencesSearchDataModule
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences(
            TRACK_HISTORY_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        )

    @Provides
    fun provideGson(): Gson = Gson()
}
