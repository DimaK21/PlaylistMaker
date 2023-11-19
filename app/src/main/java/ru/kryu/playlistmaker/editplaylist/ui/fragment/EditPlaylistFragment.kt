package ru.kryu.playlistmaker.editplaylist.ui.fragment

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.createplaylist.ui.fragment.CreatePlaylistFragment
import ru.kryu.playlistmaker.editplaylist.ui.viewmodel.EditPlaylistViewModel
import ru.kryu.playlistmaker.playlistmain.ui.model.PlaylistMainItem

class EditPlaylistFragment : CreatePlaylistFragment() {

    override val viewModel: EditPlaylistViewModel by viewModel()
    private var playlist: PlaylistMainItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playlist = getPlaylist()
        setPlaylistInfo()
        binding.btnCreateNewPlaylist.setOnClickListener {
            viewModel.onButtonSaveClick(
                playlistId = playlist?.playlistId,
                playlistName = binding.etNamePlaylist.text.toString(),
                playlistDescription = binding.etDescriptionPlaylist.text.toString(),
                playlistCoverPath = if (imageId != "") {
                    activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path + "/$FILE_DIRECTORY/cover-$imageId.jpg"
                } else {
                    playlist?.playlistCoverPath ?: ""
                }
            )
            findNavController().navigateUp()
        }
    }

    private fun getPlaylist(): PlaylistMainItem? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(PLAYLIST, PlaylistMainItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(PLAYLIST)
        }

    private fun setPlaylistInfo() {
        binding.etNamePlaylist.setText(playlist?.playlistName)
        binding.etDescriptionPlaylist.setText(playlist?.playlistDescription)
        Glide.with(this)
            .load(playlist?.playlistCoverPath)
            .transform(
                CenterCrop(),
                RoundedCorners(resources.getDimensionPixelSize(R.dimen.corners_8))
            )
            .into(binding.newCover)
        binding.btnCreateNewPlaylist.setText(getString(R.string.save))
        binding.newPlayListHead.setText(getString(R.string.edit))
    }

    override fun backPressedHandle() {
        findNavController().navigateUp()
    }

    companion object {
        private const val PLAYLIST = "PLAYLIST"
        fun createArgs(playlist: PlaylistMainItem): Bundle =
            bundleOf(PLAYLIST to playlist)
    }
}