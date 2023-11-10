package ru.kryu.playlistmaker.playlistmain.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.FragmentPlaylistMainBinding
import ru.kryu.playlistmaker.playlistmain.ui.model.PlaylistMainItem
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

    private fun render(playlistMainItem: PlaylistMainItem) {
        if (binding.tvPlaylistName.text != playlistMainItem.playlistName)
            binding.tvPlaylistName.text = playlistMainItem.playlistName
        if (binding.tvPlaylistDescription.text != playlistMainItem.playlistDescription)
            binding.tvPlaylistDescription.text = playlistMainItem.playlistDescription
        if (binding.tvPlaylistDescription.text == "")
            binding.tvPlaylistDescription.visibility = View.GONE
        if (binding.tvPlaylistDuration.text != playlistMainItem.playlistDuration.toString())
            binding.tvPlaylistDuration.text = playlistMainItem.playlistDuration.toString()
        if (binding.tvTracksInPlaylist.text != playlistMainItem.countTracks.toString())
            binding.tvTracksInPlaylist.text = playlistMainItem.countTracks.toString()
        Glide.with(this)
            .load(playlistMainItem.playlistCoverPath)
            .placeholder(R.drawable.search_placeholder_field)
            .transform(CenterCrop())
            .into(binding.albumCover)
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