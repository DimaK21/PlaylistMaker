package ru.kryu.playlistmaker.playlist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.databinding.FragmentPlaylistsBinding
import ru.kryu.playlistmaker.playlist.ui.view_model.PlaylistsViewModel
import ru.kryu.playlistmaker.playlist.domain.model.Playlist

class PlaylistsFragment : Fragment() {

    private val playlistsViewModel: PlaylistsViewModel by viewModel()

    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

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

        playlistsViewModel.listPlaylistsLiveData.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(list: List<Playlist>?) {
        if (list.isNullOrEmpty()) {
            binding.groupEmpty.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = PlaylistsFragment()
    }
}