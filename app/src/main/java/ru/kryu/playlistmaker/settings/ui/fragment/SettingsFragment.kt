package ru.kryu.playlistmaker.settings.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.app.App
import ru.kryu.playlistmaker.databinding.FragmentSettingsBinding
import ru.kryu.playlistmaker.settings.ui.viewmodel.DarkThemeState
import ru.kryu.playlistmaker.settings.ui.viewmodel.SettingsViewModel

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.darkThemeStateLiveData.observe(viewLifecycleOwner) { render(it) }

        binding.themeSwitcher.isChecked =
            (viewModel.darkThemeStateLiveData.value == DarkThemeState.STATE_DARK)
        binding.themeSwitcher.setOnCheckedChangeListener { _, checked ->
            viewModel.switcherMoved(checked)
        }

        binding.shareFrame.setOnClickListener {
            viewModel.onShareClick(getString(R.string.url_practicum))
        }

        binding.supportFrame.setOnClickListener {
            viewModel.onSupportClick(
                arrayOf(getString(R.string.email_of_developer)),
                getString(R.string.title_mail_to_developer),
                getString(R.string.thanks_to_developer)
            )
        }

        binding.agreementFrame.setOnClickListener {
            viewModel.onAgreementClick(getString(R.string.url_user_agreement))
        }
    }

    private fun render(state: DarkThemeState) {
        when (state) {
            DarkThemeState.STATE_DARK -> (activity?.application as App).switchTheme(true)
            DarkThemeState.STATE_LITE -> (activity?.application as App).switchTheme(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}