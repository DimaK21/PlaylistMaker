package ru.kryu.playlistmaker.createplaylist.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.createplaylist.ui.viewmodel.CreatePlaylistViewModel
import ru.kryu.playlistmaker.databinding.FragmentNewPlaylistBinding
import java.util.UUID

open class CreatePlaylistFragment : Fragment() {

    open val viewModel: CreatePlaylistViewModel by viewModel()
    private var _binding: FragmentNewPlaylistBinding? = null
    private val binding get() = _binding!!
    private val requester = PermissionRequester.instance()
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var editTextTextWatcher: TextWatcher
    private lateinit var editTextTextWatcher2: TextWatcher
    private lateinit var listener: (s: Editable?, textInputLayout: TextInputLayout) -> Unit
    private lateinit var confirmDialog: MaterialAlertDialogBuilder
    private var onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            backPressedHandle()
        }
    }

    /**
     * Сохраняем картинку в хранилище с идентификатором imageId.
     * Сохраняем в БД путь к картинке с этим imageId.
     *
     * Если не выбрали картинку, imageId - пустая строка. Сохраняем в БД путь к картинке пустую строку.
     */
    private var imageId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Glide.with(this)
                        .load(uri)
                        .transform(
                            CenterCrop(),
                            RoundedCorners(resources.getDimensionPixelSize(R.dimen.corners_8))
                        )
                        .into(binding.newCover)
                    if (imageId == "") imageId = UUID.randomUUID().toString()
                    viewModel.mediaPicked(uri, imageId)
                }
            }

        binding.newCover.setOnClickListener {
            pickImage()
        }

        binding.buttonBackNewPlaylist.setOnClickListener {
            backPressedHandle()
        }

        binding.btnCreateNewPlaylist.setOnClickListener {
            viewModel.onButtonSaveClick(
                playlistName = binding.etNamePlaylist.text.toString(),
                playlistDescription = binding.etDescriptionPlaylist.text.toString(),
                playlistCoverPath = if (imageId != "") {
                    activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path + "/$FILE_DIRECTORY/cover-$imageId.jpg"
                } else {
                    ""
                }
            )
            findNavController().navigateUp()
        }

        listener =
            { s, textInputLayout ->
                if (s.isNullOrEmpty()) {
                    textInputLayout.setBoxStrokeColorStateList(
                        resources.getColorStateList(
                            R.color.edit_text_color,
                            activity?.theme
                        )
                    )
                    textInputLayout.defaultHintTextColor =
                        resources.getColorStateList(R.color.black_white, activity?.theme)
                    textInputLayout.hintTextColor =
                        resources.getColorStateList(R.color.main_background, activity?.theme)
                } else {
                    textInputLayout.setBoxStrokeColorStateList(
                        resources.getColorStateList(
                            R.color.edit_text_color_second,
                            activity?.theme
                        )
                    )
                    textInputLayout.defaultHintTextColor =
                        resources.getColorStateList(R.color.main_background, activity?.theme)
                }
            }

        editTextTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.btnCreateNewPlaylist.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                listener(s, binding.ilNamePlaylist)
            }
        }
        binding.etNamePlaylist.addTextChangedListener(editTextTextWatcher)

        editTextTextWatcher2 = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                listener(s, binding.ilDescriptionPlaylist)
            }

        }
        binding.etDescriptionPlaylist.addTextChangedListener(editTextTextWatcher2)


        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.finalize_playlist))
            .setMessage(getString(R.string.unsaved_data_will_be_lost))
            .setNeutralButton(getString(R.string.cancel)) { dialog, which ->

            }.setPositiveButton(getString(R.string.finalize)) { dialog, which ->
                findNavController().navigateUp()
            }
        activity?.onBackPressedDispatcher?.addCallback(onBackPressedCallback)
    }

    private fun pickImage() {
        lifecycleScope.launch {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            }
            requester.request(permission).collect { result ->
                when (result) {
                    is PermissionResult.Granted -> {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }

                    is PermissionResult.Denied.DeniedPermanently -> {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.data = Uri.fromParts("package", context?.packageName, null)
                        context?.startActivity(intent)
                    }

                    is PermissionResult.Denied.NeedsRationale -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.need_permission),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is PermissionResult.Cancelled -> {
                        return@collect
                    }
                }
            }
        }
    }

    fun backPressedHandle() {
        if (!binding.etNamePlaylist.text.isNullOrEmpty() ||
            !binding.etDescriptionPlaylist.text.isNullOrEmpty() ||
            !imageId.isEmpty()
        ) {
            confirmDialog.show()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        binding.etNamePlaylist.removeTextChangedListener(editTextTextWatcher)
        binding.etDescriptionPlaylist.removeTextChangedListener(editTextTextWatcher2)
        _binding = null
    }

    companion object {
        const val FILE_DIRECTORY = "covers"
    }
}