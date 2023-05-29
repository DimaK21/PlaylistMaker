package ru.kryu.playlistmaker

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var userText: String = ""
    private var lastRequest: String = ""
    private val editText: EditText by lazy { findViewById(R.id.edit_text_search) }
    private val viewNotFound: LinearLayout by lazy { findViewById(R.id.not_found) }
    private val viewNoConnection: LinearLayout by lazy { findViewById(R.id.no_connection) }
    private val progressBar: ProgressBar by lazy { findViewById(R.id.progress_bar) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler_view_search) }
    private val buttonRefresh: Button by lazy { findViewById(R.id.button_refresh) }
    private val historyTitleTv: TextView by lazy { findViewById(R.id.history_title) }
    private val buttonClearHistory: Button by lazy { findViewById(R.id.clear_history_button) }

    private val trackList = ArrayList<Track>()
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackHistoryAdapter: TrackHistoryAdapter

    private val iTunesBaseUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit
        .Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val iTunesApiService = retrofit.create(ITunesApiService::class.java)

    private lateinit var searchHistory: SearchHistory

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val TRACKS = "TRACKS"
        const val TRACK_HISTORY_PREFERENCES = "track_history_preferences"
        const val TRACK_HISTORY_KEY = "track_history_key"
    }

    enum class SearchVisibilityState {
        SEARCH_RESULT_SUCCESS_OR_NO_HISTORY,
        SEARCH_RESULT_NOT_FOUND,
        SEARCH_RESULT_ERROR,
        HISTORY,
        REST_REQUEST,
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_TEXT, userText)
        outState.putParcelableArrayList(TRACKS, trackList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        userText = savedInstanceState.getString(SEARCH_TEXT, "")
        if (userText.isNotEmpty()) {
            editText.setText(userText)
            editText.setSelection(editText.text.length)
        }
        val list = savedInstanceState.parcelableArrayList<Track>(TRACKS)
        if (!list.isNullOrEmpty()) {
            trackList.addAll(list)
        }
    }

    private inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val onTrackClickListener = TrackAdapter.OnTrackClickListener { track: Track ->
            searchHistory.addTrack(track)
        }
        trackAdapter = TrackAdapter(trackList, onTrackClickListener)

        val sharedPreferences = getSharedPreferences(TRACK_HISTORY_PREFERENCES, MODE_PRIVATE)
        searchHistory = SearchHistory(sharedPreferences)
        trackHistoryAdapter =
            TrackHistoryAdapter(searchHistory.listTrackHistory as ArrayList<Track>)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        if (searchHistory.listTrackHistory.isEmpty()) {
            recyclerView.adapter = trackAdapter
            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
        } else {
            recyclerView.adapter = trackHistoryAdapter
            manageVisibility(SearchVisibilityState.HISTORY)
        }

        val buttonBack = findViewById<ImageView>(R.id.search_arrow)
        buttonBack.setOnClickListener {
            finish()
        }

        val clearButton = findViewById<ImageView>(R.id.edit_text_clear)
        clearButton.setOnClickListener {
            clearButtonOnClick()
        }

        val editTextTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                if (!s.isNullOrEmpty()) {
                    manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
                    recyclerView.adapter = trackAdapter
                }
            }

            override fun afterTextChanged(s: Editable?) {
                userText = s.toString()
            }
        }

        editText.addTextChangedListener(editTextTextWatcher)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (trackList.isNotEmpty()) {
                    trackList.clear()
                    trackAdapter.notifyDataSetChanged()
                }
                refreshTrackList(editText.text.toString())
                true
            }
            false
        }

        buttonRefresh.setOnClickListener {
            refreshTrackList(lastRequest)
        }

        buttonClearHistory.setOnClickListener {
            searchHistory.clearTrackHistory()
            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
            recyclerView.adapter = trackAdapter
        }
    }

    override fun onStop() {
        super.onStop()
        searchHistory.saveTrackHistory()
    }

    private fun clearButtonOnClick() {
        editText.setText("")
        editText.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        if (searchHistory.listTrackHistory.isEmpty()) {
            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
        } else {
            recyclerView.adapter = trackHistoryAdapter
            manageVisibility(SearchVisibilityState.HISTORY)
        }
        if (trackList.isNotEmpty()) {
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
        }
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun refreshTrackList(requestText: String) {
        manageVisibility(SearchVisibilityState.REST_REQUEST)
        lastRequest = requestText
        iTunesApiService.search(lastRequest).enqueue(
            object : Callback<ITunesResponse> {
                override fun onResponse(
                    call: Call<ITunesResponse>,
                    response: Response<ITunesResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.results?.isNotEmpty()!!) {
                            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
                            trackList.addAll(response.body()?.results!!)
                            trackAdapter.notifyDataSetChanged()
                        } else {
                            manageVisibility(SearchVisibilityState.SEARCH_RESULT_NOT_FOUND)
                        }
                    } else {
                        manageVisibility(SearchVisibilityState.SEARCH_RESULT_ERROR)
                    }
                }

                override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                    manageVisibility(SearchVisibilityState.SEARCH_RESULT_ERROR)
                }

            }
        )
    }

    private fun manageVisibility(state: SearchVisibilityState) {
        when (state) {
            SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY -> {
                viewNotFound.visibility = View.GONE
                viewNoConnection.visibility = View.GONE
                progressBar.visibility = View.GONE
                historyTitleTv.visibility = View.GONE
                buttonClearHistory.visibility = View.GONE

            }

            SearchVisibilityState.SEARCH_RESULT_NOT_FOUND -> {
                viewNotFound.visibility = View.VISIBLE
                viewNoConnection.visibility = View.GONE
                progressBar.visibility = View.GONE
                historyTitleTv.visibility = View.GONE
                buttonClearHistory.visibility = View.GONE
            }

            SearchVisibilityState.SEARCH_RESULT_ERROR -> {
                viewNotFound.visibility = View.GONE
                viewNoConnection.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                historyTitleTv.visibility = View.GONE
                buttonClearHistory.visibility = View.GONE
            }

            SearchVisibilityState.HISTORY -> {
                viewNotFound.visibility = View.GONE
                viewNoConnection.visibility = View.GONE
                progressBar.visibility = View.GONE
                historyTitleTv.visibility = View.VISIBLE
                buttonClearHistory.visibility = View.VISIBLE
            }

            SearchVisibilityState.REST_REQUEST -> {
                viewNotFound.visibility = View.GONE
                viewNoConnection.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                historyTitleTv.visibility = View.GONE
                buttonClearHistory.visibility = View.GONE
            }
        }
    }
}
