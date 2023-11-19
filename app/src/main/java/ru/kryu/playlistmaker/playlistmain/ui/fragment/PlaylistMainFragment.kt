package ru.kryu.playlistmaker.playlistmain.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.FragmentPlaylistMainBinding
import ru.kryu.playlistmaker.editplaylist.ui.fragment.EditPlaylistFragment
import ru.kryu.playlistmaker.player.ui.fragment.AudioPlayerFragment
import ru.kryu.playlistmaker.playlistmain.ui.model.PlaylistMainItem
import ru.kryu.playlistmaker.playlistmain.ui.recycler.TrackAdapterLongClick
import ru.kryu.playlistmaker.playlistmain.ui.viewmodel.PlaylistMainViewModel
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class PlaylistMainFragment : Fragment() {

    private var _binding: FragmentPlaylistMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlaylistMainViewModel by viewModel {
        parametersOf(arguments?.getLong(PLAYLISTID))
    }
    private var trackAdapter: TrackAdapterLongClick? = null
    private var isClickAllowed = true
    private lateinit var bottomSheetBehaviorMenu: BottomSheetBehavior<LinearLayout>

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

        binding.rvTracks.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        trackAdapter = TrackAdapterLongClick(
            onTrackClickListener = {
                if (clickDebounce()) {
                    findNavController().navigate(
                        R.id.action_playlistMainFragment_to_audioPlayerFragment,
                        AudioPlayerFragment.createArgs(it)
                    )
                }
            },
            onTrackLongClickListener = {
                showDialog(it)
            }
        )
        binding.rvTracks.adapter = trackAdapter

        viewModel.playlistMainLiveData.observe(viewLifecycleOwner) {
            render(it)
        }

        binding.buttonBackPlaylist.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivShare.setOnClickListener {
            share()
        }
        bottomSheetBehaviorMenu = BottomSheetBehavior.from(binding.bsMenu)
        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
        binding.ivSettings.setOnClickListener {
            bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_EXPANDED
        }
        bottomSheetBehaviorMenu.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                        binding.overlay.isClickable = false
                    }

                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                        binding.overlay.isClickable = true
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.overlay.alpha = slideOffset + 1
            }
        })
        binding.tvShare.setOnClickListener {
            share()
        }
        binding.tvEdit.setOnClickListener {
            val playlistMainItem = viewModel.playlistMainLiveData.value?.copy(tracks = emptyList())
            findNavController().navigate(
                R.id.action_playlistMainFragment_to_editPlaylistFragment,
                EditPlaylistFragment.createArgs(
                    playlistMainItem!!
                )
            )
        }
        binding.tvDelete.setOnClickListener {
            bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
            showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_playlist))
            .setMessage(getString(R.string.want_delete_playlist))
            .setNegativeButton(getString(R.string.no)) { dialog, which ->

            }.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                viewModel.playlistMainLiveData.removeObservers(viewLifecycleOwner)
                viewModel.deletePlaylistClicked()
                findNavController().navigateUp()
            }
            .show()
    }

    private fun share() {
        if (trackAdapter?.trackList.isNullOrEmpty()) {
            Snackbar.make(binding.root, getString(R.string.no_tracks), Snackbar.LENGTH_LONG)
                .show()
        } else {
            viewModel.shareClicked(binding.tvTracksInPlaylist.text.toString())
        }
    }

    private fun showDialog(track: TrackForUi) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.want_delete_track))
            .setNegativeButton(getString(R.string.no)) { dialog, which ->

            }.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                val position = trackAdapter?.trackList?.indexOf(track)
                trackAdapter?.trackList?.remove(track)
                trackAdapter?.notifyItemRemoved(position!!)
                trackAdapter?.notifyItemRangeChanged(position!!, trackAdapter?.trackList?.size!!)
                viewModel.trackRemoved(track)
            }
            .show()
    }

    private fun render(playlistMainItem: PlaylistMainItem) {
        if (binding.tvPlaylistName.text != playlistMainItem.playlistName)
            binding.tvPlaylistName.text = playlistMainItem.playlistName
        if (binding.tvPlaylistDescription.text != playlistMainItem.playlistDescription)
            binding.tvPlaylistDescription.text = playlistMainItem.playlistDescription
        if (binding.tvPlaylistDescription.text == "") {
            binding.tvPlaylistDescription.visibility = View.GONE
        } else {
            binding.tvPlaylistDescription.visibility = View.VISIBLE
        }
        val duration = "${playlistMainItem.playlistDuration} ${
            resources.getQuantityString(
                R.plurals.number_of_minutes,
                playlistMainItem.playlistDuration.toInt()
            )
        }"
        if (binding.tvPlaylistDuration.text != duration)
            binding.tvPlaylistDuration.text = duration
        val countTracks = "${playlistMainItem.countTracks} ${
            resources.getQuantityString(
                R.plurals.number_of_tracks,
                playlistMainItem.countTracks
            )
        }"
        if (binding.tvTracksInPlaylist.text != countTracks)
            binding.tvTracksInPlaylist.text = countTracks
        Glide.with(this)
            .load(playlistMainItem.playlistCoverPath)
            .placeholder(R.drawable.search_placeholder_field)
            .transform(CenterCrop())
            .into(binding.albumCover)
        if (trackAdapter?.trackList.isNullOrEmpty()) {
            trackAdapter?.trackList?.addAll(playlistMainItem.tracks)
            trackAdapter?.notifyDataSetChanged()
        }
        if (binding.menuTitle.tvPlaylistNameSmall.text != playlistMainItem.playlistName)
            binding.menuTitle.tvPlaylistNameSmall.text = playlistMainItem.playlistName
        if (binding.menuTitle.tvPlaylistSongsNumberSmall.text != countTracks)
            binding.menuTitle.tvPlaylistSongsNumberSmall.text = countTracks
        Glide.with(this)
            .load(playlistMainItem.playlistCoverPath)
            .placeholder(R.drawable.search_placeholder_field)
            .transform(
                CenterCrop(),
                RoundedCorners(resources.getDimensionPixelSize(R.dimen.image_track_corners))
            )
            .into(binding.menuTitle.ivPlaylistImageSmall)
        if (playlistMainItem.tracks.isEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.no_tracks_short),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trackAdapter = null
        binding.rvTracks.adapter = null
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

        private const val PLAYLISTID = "playlistId"
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
        fun createArgs(playlistId: Long): Bundle = bundleOf(PLAYLISTID to playlistId)
    }
}