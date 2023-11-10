package ru.kryu.playlistmaker.playlistmain.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.kryu.playlistmaker.databinding.FragmentPlaylistMainBinding
import ru.kryu.playlistmaker.playlistmain.domain.model.PlaylistMain
import ru.kryu.playlistmaker.playlistmain.ui.viewmodel.PlaylistMainViewModel

class PlaylistMainFragment : Fragment() {

    private var _binding: FragmentPlaylistMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlaylistMainViewModel by viewModel {
        parametersOf(arguments?.getLong(PLAYLISTID))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.playlistMainLiveData.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.initPlaylistInfo()
    }

    private fun render(playlistMain: PlaylistMain) {
        binding.tvPlaylistName.text = playlistMain.playlist.playlistName
        binding.tvPlaylistDescription.text = playlistMain.playlist.playlistDescription
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val PLAYLISTID = "playlistId"
        fun createArgs(playlistId: Long): Bundle = bundleOf(PLAYLISTID to playlistId)
    }
}