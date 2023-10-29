package ru.kryu.playlistmaker.playlists.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.FragmentPlaylistsBinding
import ru.kryu.playlistmaker.playlists.ui.recycler.PlaylistAdapter
import ru.kryu.playlistmaker.playlists.ui.viewmodel.PlaylistsState
import ru.kryu.playlistmaker.playlists.ui.viewmodel.PlaylistsViewModel

class PlaylistsFragment : Fragment() {

    private val playlistsViewModel: PlaylistsViewModel by viewModel()

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!
    private var playlistAdapter: PlaylistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlistAdapter = PlaylistAdapter()
        binding.rvPlaylists.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.rvPlaylists.adapter = playlistAdapter

        playlistsViewModel.playlistsLiveData.observe(viewLifecycleOwner) {
            render(it)
        }

        playlistsViewModel.viewCreated()

        binding.btnNewPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_createPlaylistFragment)
        }
    }

    private fun render(state: PlaylistsState) {
        when (state) {
            is PlaylistsState.Content -> {
                binding.groupEmpty.visibility = View.GONE
                binding.rvPlaylists.visibility = View.VISIBLE

                playlistAdapter?.playlists?.clear()
                playlistAdapter?.playlists?.addAll(state.playlists)
                playlistAdapter?.notifyDataSetChanged()
            }

            PlaylistsState.Empty -> {
                binding.groupEmpty.visibility = View.VISIBLE
                binding.rvPlaylists.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playlistAdapter = null
        binding.rvPlaylists.adapter = null
        _binding = null
    }

    companion object {
        fun newInstance() = PlaylistsFragment()
    }
}