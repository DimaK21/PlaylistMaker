package ru.kryu.playlistmaker.search.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.FragmentSearchBinding
import ru.kryu.playlistmaker.player.ui.fragment.AudioPlayerFragment
import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.ui.recycler.TrackAdapter
import ru.kryu.playlistmaker.search.ui.viewmodel.SearchViewModel
import ru.kryu.playlistmaker.search.ui.viewmodel.TrackSearchState

class SearchFragment : Fragment() {

    private var lastRequest: String = ""
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var isClickAllowed = true
    private val viewModel: SearchViewModel by viewModel()
    private lateinit var editTextTextWatcher: TextWatcher

    private var trackAdapter: TrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        trackAdapter = TrackAdapter {
            if (isClickAllowed) {
                findNavController().navigate(
                    R.id.action_searchFragment_to_audioPlayerFragment,
                    AudioPlayerFragment.createArgs(it)
                )
                viewModel.onTrackClick(it)
            }
        }
        binding.recyclerViewSearch.adapter = trackAdapter
        binding.recyclerViewSearch.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

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

        viewModel.observeStateLiveData().observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.isClickAllowedLiveData.observe(viewLifecycleOwner) {
            isClickAllowed = it
        }
        viewModel.observeToastLiveData().observe(viewLifecycleOwner) {
            showToast(it)
        }

        binding.buttonRefresh.setOnClickListener {
            if (isClickAllowed) {
                viewModel.searchWithoutDebounce(lastRequest)
            }
        }

        binding.clearHistoryButton.setOnClickListener {
            viewModel.onClearTrackHistoryClick()
        }

        viewModel.onViewCreated()
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.editTextSearch.removeTextChangedListener(editTextTextWatcher)
        trackAdapter = null
        _binding = null
    }

    private fun render(state: TrackSearchState) {
        when (state) {
            is TrackSearchState.History -> showHistory(state.tracks)
            is TrackSearchState.Content -> showContent(state.tracks)
            is TrackSearchState.Empty -> showEmpty()
            is TrackSearchState.Error -> showError()
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

        trackAdapter?.trackList?.clear()
        trackAdapter?.trackList?.addAll(tracksHistory)
        trackAdapter?.notifyDataSetChanged()
    }

    private fun showContent(tracks: List<TrackForUi>) {
        binding.notFound.visibility = View.GONE
        binding.noConnection.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.historyTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.VISIBLE

        trackAdapter?.trackList?.clear()
        trackAdapter?.trackList?.addAll(tracks)
        trackAdapter?.notifyDataSetChanged()
    }

    private fun showEmpty() {
        binding.notFound.visibility = View.VISIBLE
        binding.noConnection.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.historyTitle.visibility = View.GONE
        binding.clearHistoryButton.visibility = View.GONE
        binding.recyclerViewSearch.visibility = View.GONE
    }

    private fun showError() {
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
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        viewModel.onClearButtonClick()
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}