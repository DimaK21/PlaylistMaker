package ru.kryu.playlistmaker.ui.search

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
import ru.kryu.playlistmaker.Creator
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.domain.models.Track
import ru.kryu.playlistmaker.presentation.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.presentation.search.SearchHistory
import ru.kryu.playlistmaker.presentation.models.TrackForUi
import ru.kryu.playlistmaker.ui.player.AudioPlayerActivity

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

    private val trackList = ArrayList<TrackForUi>()
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackHistoryAdapter: TrackAdapter

    private val creator = Creator
    private val trackSearchInteractor = creator.provideTrackSearchInteractor()

    private lateinit var searchHistory: SearchHistory

    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private var searchRunnable = Runnable { refreshTrackList(editText.text.toString()) }

    private var isClickAllowed = true

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
        val list = savedInstanceState.parcelableArrayList<TrackForUi>(TRACKS)
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

        val onTrackClickListener = TrackAdapter.OnTrackClickListener { track: TrackForUi ->
            if (clickDebounce()) {
                searchHistory.addTrack(track)
                val audioPlayerActivityIntent = Intent(this, AudioPlayerActivity::class.java)
                audioPlayerActivityIntent.putExtra(TRACK, track)
                startActivity(audioPlayerActivityIntent)
            }
        }
        trackAdapter = TrackAdapter(trackList, onTrackClickListener)

        searchHistory = SearchHistory(applicationContext)
        trackHistoryAdapter =
            TrackAdapter(searchHistory.listTrackHistory as ArrayList<TrackForUi>, onTrackClickListener)
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
                searchDebounce()
            }

            override fun afterTextChanged(s: Editable?) {
                userText = s.toString()
            }
        }

        editText.addTextChangedListener(editTextTextWatcher)

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
        if (trackList.isNotEmpty()) {
            trackList.clear()
            trackAdapter.notifyDataSetChanged()
        }
        manageVisibility(SearchVisibilityState.REST_REQUEST)
        lastRequest = requestText
        trackSearchInteractor.searchTracks(lastRequest) { responseList: List<Track>? ->
            if (responseList == null) {
                handlerMainLooper.post { manageVisibility(SearchVisibilityState.SEARCH_RESULT_ERROR) }
            } else if (responseList.isEmpty()) {
                handlerMainLooper.post { manageVisibility(SearchVisibilityState.SEARCH_RESULT_NOT_FOUND) }
            } else {
                handlerMainLooper.post {
                    manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
                    trackList.addAll(responseList.map { TrackToTrackForUi().trackToTrackForUi(it) })
                    trackAdapter.notifyDataSetChanged()
                }
            }
        }
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

    override fun onStart() {
        super.onStart()
        trackHistoryAdapter.notifyDataSetChanged()
    }

    private fun searchDebounce() {
        handlerMainLooper.removeCallbacks(searchRunnable)
        if (editText.text.isNotEmpty()) {
            handlerMainLooper.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY_MILLIS)
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handlerMainLooper.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY_MILLIS)
        }
        return current
    }

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        const val TRACKS = "TRACKS"
        const val TRACK = "TRACK"
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }

    enum class SearchVisibilityState {
        SEARCH_RESULT_SUCCESS_OR_NO_HISTORY,
        SEARCH_RESULT_NOT_FOUND,
        SEARCH_RESULT_ERROR,
        HISTORY,
        REST_REQUEST,
    }
}
