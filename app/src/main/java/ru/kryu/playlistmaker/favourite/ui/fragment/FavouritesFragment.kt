package ru.kryu.playlistmaker.favourite.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.FragmentFavouritesBinding
import ru.kryu.playlistmaker.favourite.ui.viewmodel.FavouritesState
import ru.kryu.playlistmaker.favourite.ui.viewmodel.FavouritesViewModel
import ru.kryu.playlistmaker.player.ui.fragment.AudioPlayerFragment
import ru.kryu.playlistmaker.search.ui.models.TrackForUi
import ru.kryu.playlistmaker.search.ui.recycler.TrackAdapter

class FavouritesFragment : Fragment() {

    private val favouritesViewModel: FavouritesViewModel by viewModel()

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private var trackAdapter: TrackAdapter? = null
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter = TrackAdapter {
            if (clickDebounce()) {
                findNavController().navigate(
                    R.id.action_mediaFragment_to_audioPlayerFragment,
                    AudioPlayerFragment.createArgs(it)
                ).apply { it.isFavorite = true }
            }
        }
        binding.recyclerViewFavourites.adapter = trackAdapter
        binding.recyclerViewFavourites.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        favouritesViewModel.favouritesLiveData.observe(viewLifecycleOwner) {
            render(it)
        }
        favouritesViewModel.onViewCreatedOnScreen()
    }

    private fun render(state: FavouritesState) {
        when (state) {
            is FavouritesState.Content -> showContent(state.tracks)
            FavouritesState.Empty -> showEmpty()
        }
    }

    private fun showEmpty() {
        binding.groupEmpty.visibility = View.VISIBLE
        binding.recyclerViewFavourites.visibility = View.GONE
    }

    private fun showContent(tracks: List<TrackForUi>) {
        binding.groupEmpty.visibility = View.GONE
        binding.recyclerViewFavourites.visibility = View.VISIBLE

        trackAdapter?.trackList?.clear()
        trackAdapter?.trackList?.addAll(tracks)
        trackAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trackAdapter = null
        binding.recyclerViewFavourites.adapter = null
        _binding = null
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        fun newInstance() = FavouritesFragment()
    }
}