package ru.kryu.playlistmaker.search.ui.activity

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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.playlistmaker.creator.Creator
import ru.kryu.playlistmaker.databinding.ActivitySearchBinding
import ru.kryu.playlistmaker.player.ui.activity.AudioPlayerActivity
import ru.kryu.playlistmaker.player.ui.mapper.TrackToTrackForUi
import ru.kryu.playlistmaker.player.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.SearchHistory
import ru.kryu.playlistmaker.search.ui.TrackAdapter

class SearchActivity : AppCompatActivity() {

    private var userText: String = ""
    private var lastRequest: String = ""
    private lateinit var binding: ActivitySearchBinding
    private val trackList = ArrayList<TrackForUi>()
    private lateinit var trackAdapter: TrackAdapter
    private lateinit var trackHistoryAdapter: TrackAdapter
    private val creator = Creator
    private val trackSearchInteractor = creator.provideTrackSearchInteractor()
    private lateinit var searchHistory: SearchHistory
    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private lateinit var searchRunnable: Runnable
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
            binding.editTextSearch.setText(userText)
            binding.editTextSearch.setSelection(binding.editTextSearch.text.length)
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
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchRunnable = Runnable { refreshTrackList(binding.editTextSearch.text.toString()) }
        searchHistory = SearchHistory(applicationContext)

        val onTrackClickListener = TrackAdapter.OnTrackClickListener { track: TrackForUi ->
            if (clickDebounce()) {
                searchHistory.addTrack(track)
                val audioPlayerActivityIntent = Intent(this, AudioPlayerActivity::class.java)
                audioPlayerActivityIntent.putExtra(TRACK, track)
                startActivity(audioPlayerActivityIntent)
            }
        }
        trackAdapter = TrackAdapter(trackList, onTrackClickListener)

        trackHistoryAdapter =
            TrackAdapter(
                searchHistory.listTrackHistory as ArrayList<TrackForUi>,
                onTrackClickListener
            )
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        if (searchHistory.listTrackHistory.isEmpty()) {
            binding.recyclerViewSearch.adapter = trackAdapter
            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
        } else {
            binding.recyclerViewSearch.adapter = trackHistoryAdapter
            manageVisibility(SearchVisibilityState.HISTORY)
        }

        binding.buttonBackSearch.setOnClickListener {
            finish()
        }

        binding.clearButton.setOnClickListener {
            clearButtonOnClick()
        }

        val editTextTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearButton.visibility = clearButtonVisibility(s)
                if (!s.isNullOrEmpty()) {
                    manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
                    binding.recyclerViewSearch.adapter = trackAdapter
                }
                searchDebounce()
            }

            override fun afterTextChanged(s: Editable?) {
                userText = s.toString()
            }
        }

        binding.editTextSearch.addTextChangedListener(editTextTextWatcher)

        binding.buttonRefresh.setOnClickListener {
            refreshTrackList(lastRequest)
        }

        binding.clearHistoryButton.setOnClickListener {
            searchHistory.clearTrackHistory()
            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
            binding.recyclerViewSearch.adapter = trackAdapter
        }
    }

    override fun onStop() {
        super.onStop()
        searchHistory.saveTrackHistory()
    }

    private fun clearButtonOnClick() {
        binding.editTextSearch.setText("")
        binding.editTextSearch.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        if (searchHistory.listTrackHistory.isEmpty()) {
            manageVisibility(SearchVisibilityState.SEARCH_RESULT_SUCCESS_OR_NO_HISTORY)
        } else {
            binding.recyclerViewSearch.adapter = trackHistoryAdapter
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
                binding.notFound.visibility = View.GONE
                binding.noConnection.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.historyTitle.visibility = View.GONE
                binding.clearHistoryButton.visibility = View.GONE
            }

            SearchVisibilityState.SEARCH_RESULT_NOT_FOUND -> {
                binding.notFound.visibility = View.VISIBLE
                binding.noConnection.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.historyTitle.visibility = View.GONE
                binding.clearHistoryButton.visibility = View.GONE
            }

            SearchVisibilityState.SEARCH_RESULT_ERROR -> {
                binding.notFound.visibility = View.GONE
                binding.noConnection.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.historyTitle.visibility = View.GONE
                binding.clearHistoryButton.visibility = View.GONE
            }

            SearchVisibilityState.HISTORY -> {
                binding.notFound.visibility = View.GONE
                binding.noConnection.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.historyTitle.visibility = View.VISIBLE
                binding.clearHistoryButton.visibility = View.VISIBLE
            }

            SearchVisibilityState.REST_REQUEST -> {
                binding.notFound.visibility = View.GONE
                binding.noConnection.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.historyTitle.visibility = View.GONE
                binding.clearHistoryButton.visibility = View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        trackHistoryAdapter.notifyDataSetChanged()
    }

    private fun searchDebounce() {
        handlerMainLooper.removeCallbacks(searchRunnable)
        if (binding.editTextSearch.text.isNotEmpty()) {
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
