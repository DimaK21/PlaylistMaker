package ru.kryu.playlistmaker.search.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kryu.playlistmaker.databinding.ActivitySearchBinding
import ru.kryu.playlistmaker.player.ui.activity.AudioPlayerActivity
import ru.kryu.playlistmaker.search.ui.recycler.TrackAdapter
import ru.kryu.playlistmaker.search.ui.view_model.TrackSearchState
import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.ui.view_model.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private var lastRequest: String = ""
    private lateinit var binding: ActivitySearchBinding
    private val handlerMainLooper = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    private lateinit var viewModel: SearchViewModel
    private lateinit var editTextTextWatcher: TextWatcher

    private val trackAdapter = TrackAdapter {
        if (clickDebounce()) {
            val audioPlayerActivityIntent = Intent(this, AudioPlayerActivity::class.java)
            audioPlayerActivityIntent.putExtra(TRACK, it)
            startActivity(audioPlayerActivityIntent)
            viewModel.addTrack(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]

        binding.recyclerViewSearch.adapter = trackAdapter
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.buttonBackSearch.setOnClickListener {
            finish()
        }

        binding.clearButton.setOnClickListener {
            clearButtonOnClick()
        }

        editTextTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearButton.visibility = clearButtonVisibility(s)
                lastRequest = s?.toString() ?: ""
                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }

        binding.editTextSearch.addTextChangedListener(editTextTextWatcher)

        viewModel.observeStateLiveData().observe(this) {
            render(it)
        }

        binding.buttonRefresh.setOnClickListener {
            if (clickDebounce()) {
                viewModel.searchWithoutDebounce(lastRequest)
            }
        }

        binding.clearHistoryButton.setOnClickListener {
            viewModel.clearTrackHistory()
            trackAdapter.trackList.clear()
            trackAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.editTextSearch.removeTextChangedListener(editTextTextWatcher)
    }

    private fun render(state: TrackSearchState) {
        when (state) {
            is TrackSearchState.History -> showHistory(state.tracks)
            is TrackSearchState.Content -> showContent(state.tracks)
            is TrackSearchState.Empty -> showEmpty(state.message)
            is TrackSearchState.Error -> showError(state.errorMessage)
            TrackSearchState.Loading -> showLoading()
        }
    }

    private fun showHistory(tracksHistory: List<TrackForUi>) {
        binding.notFound.visibility = View.GONE
        binding.noConnection.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.historyTitle.visibility = View.VISIBLE
        binding.clearHistoryButton.visibility = View.VISIBLE
        binding.recyclerViewSearch.visibility = View.VISIBLE

        trackAdapter.trackList.clear()
        trackAdapter.trackList.addAll(tracksHistory)
        trackAdapter.notifyDataSetChanged()
    }

    private fun showContent(tracks: List<TrackForUi>) {
        binding.notFound.visibility = View.GONE
        binding.noConnection.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.historyTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.VISIBLE

        trackAdapter.trackList.clear()
        trackAdapter.trackList.addAll(tracks)
        trackAdapter.notifyDataSetChanged()
    }

    private fun showEmpty(message: String) {
        binding.notFound.visibility = View.VISIBLE
        binding.noConnection.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.historyTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        binding.notFound.visibility = View.GONE
        binding.noConnection.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.historyTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun showLoading() {
        binding.notFound.visibility = View.GONE
        binding.noConnection.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.historyTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun clearButtonOnClick() {
        binding.editTextSearch.setText("")
        binding.editTextSearch.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.windowToken, 0)
        showHistory(viewModel.tracksHistory)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
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
        const val TRACK = "TRACK"
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}
