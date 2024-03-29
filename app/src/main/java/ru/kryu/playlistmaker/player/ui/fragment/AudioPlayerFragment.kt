package ru.kryu.playlistmaker.player.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.FragmentAudioPlayerBinding
import ru.kryu.playlistmaker.player.ui.recycler.PlaylistInPlayerAdapter
import ru.kryu.playlistmaker.player.ui.viewmodel.AudioPlayerViewModel
import ru.kryu.playlistmaker.player.ui.viewmodel.PlayerState
import ru.kryu.playlistmaker.player.ui.viewmodel.TrackInPlaylistState
import ru.kryu.playlistmaker.playlists.ui.models.PlaylistItemUi
import ru.kryu.playlistmaker.search.domain.model.Track
import ru.kryu.playlistmaker.search.ui.mapper.TrackForUiMapper
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class AudioPlayerFragment : Fragment() {

    private var _binding: FragmentAudioPlayerBinding? = null
    private val binding get() = _binding!!
    private var playlistAdapter: PlaylistInPlayerAdapter? = null
    private lateinit var track: TrackForUi
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private val viewModel: AudioPlayerViewModel by viewModel {
        parametersOf(track)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        track = getTrack()
        viewModel.playerStateLiveData.observe(viewLifecycleOwner) { render(it) }
        viewModel.playerPositionLiveData.observe(viewLifecycleOwner) { setTimer(it) }
        viewModel.isFavouriteLiveData.observe(viewLifecycleOwner) { setLikeIcon(it) }
        viewModel.listPlaylistsLiveData.observe(viewLifecycleOwner) { setListInAdapter(it) }
        viewModel.observeMessageLiveData().observe(viewLifecycleOwner) { showMessage(it) }
        binding.buttonBackPlayer.setOnClickListener {
            findNavController().navigateUp()
        }
        initTrackInfo()
        binding.playButton.setOnClickListener {
            viewModel.onPlayerButtonClick()
        }
        binding.likeButton.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bsPlaylists)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.addButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            viewModel.onAddButtonClicked()
        }
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }

                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.overlay.alpha = slideOffset + 0.6f
            }
        })
        playlistAdapter = PlaylistInPlayerAdapter { playlist ->
            viewModel.onPlaylistClicked(playlist)
        }
        binding.rvPlaylistsSmall.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvPlaylistsSmall.adapter = playlistAdapter
        binding.btnNewPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_audioPlayerFragment_to_createPlaylistFragment)
        }
    }

    private fun showMessage(trackInPlaylistState: TrackInPlaylistState) {
        val message: String
        when (trackInPlaylistState) {
            is TrackInPlaylistState.TrackInPlaylistAdded -> {
                message = getString(R.string.added_in_playlist, trackInPlaylistState.playlistName)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }

            is TrackInPlaylistState.TrackInPlaylistNotAdded -> {
                message =
                    getString(R.string.not_added_in_playlist, trackInPlaylistState.playlistName)
            }

        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setListInAdapter(playlists: List<PlaylistItemUi>?) {
        if (playlists != null) {
            playlistAdapter?.playlists?.clear()
            playlistAdapter?.playlists?.addAll(playlists)
            playlistAdapter?.notifyDataSetChanged()
        }
    }

    private fun setLikeIcon(isFavourite: Boolean) {
        if (isFavourite) {
            binding.likeButton.background = getDrawable(requireContext(), R.drawable.button_like_yes)
        } else {
            binding.likeButton.background = getDrawable(requireContext(), R.drawable.button_like_no)
        }
    }

    private fun setTimer(time: String?) {
        binding.timer.text = time
    }

    private fun getTrack(): TrackForUi =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(TRACK, TrackForUi::class.java)
                ?: TrackForUiMapper.map(
                    Track()
                )
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(TRACK) ?: TrackForUiMapper.map(
                Track()
            )
        }

    private fun initTrackInfo() {
        Glide.with(this)
            .load(track.artworkUrl512)
            .placeholder(R.drawable.search_placeholder)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corners_8)))
            .into(binding.albumCover)
        binding.trackNameTv.text = track.trackName
        binding.artistNameTv.text = track.artistName
        binding.fullTrackTimeCurrentTv.text = track.getFormatTrackTimeMillis()
        binding.albumCurrentTv.text = track.collectionName
        if (track.collectionName == "") {
            binding.albumGroup.visibility = View.GONE
        }
        binding.yearCurrentTv.text = track.releaseDate.substring(START_INDEX, END_INDEX)
        binding.genreCurrentTv.text = track.primaryGenreName
        binding.countryCurrentTv.text = track.country
    }

    override fun onPause() {
        super.onPause()
        viewModel.noScreen()
    }

    private fun render(state: PlayerState) {
        when (state) {
            PlayerState.STATE_DEFAULT -> renderStateDefault()
            PlayerState.STATE_PREPARED -> renderStatePrepared()
            PlayerState.STATE_PLAYING -> renderStatePlaying()
            PlayerState.STATE_PAUSED -> renderStatePaused()
        }
    }

    private fun renderStatePaused() {
        binding.playButton.setImageResource(R.drawable.play_button)
    }

    private fun renderStatePlaying() {
        binding.playButton.setImageResource(R.drawable.stop_button)
    }

    private fun renderStatePrepared() {
        binding.playButton.isEnabled = true
        binding.playButton.setImageResource(R.drawable.play_button)
    }

    private fun renderStateDefault() {
        binding.playButton.isEnabled = false
        binding.playButton.setImageResource(R.drawable.play_button_gray)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playlistAdapter = null
        binding.rvPlaylistsSmall.adapter = null
        _binding = null
    }

    companion object {
        private const val TRACK = "TRACK"
        private const val START_INDEX = 0
        private const val END_INDEX = 4

        fun createArgs(track: TrackForUi): Bundle =
            bundleOf(TRACK to track)
    }
}