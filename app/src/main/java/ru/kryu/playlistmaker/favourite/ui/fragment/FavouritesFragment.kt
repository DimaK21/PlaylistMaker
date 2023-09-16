package ru.kryu.playlistmaker.favourite.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kryu.playlistmaker.databinding.FragmentFavouritesBinding
import ru.kryu.playlistmaker.favourite.ui.view_model.FavouritesViewModel
import ru.kryu.playlistmaker.search.ui.models.TrackForUi

class FavouritesFragment : Fragment() {

    private val favouritesViewModel: FavouritesViewModel by viewModel()

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

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

        favouritesViewModel.listFavouritesLiveData.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(list: List<TrackForUi>?) {
        if (list.isNullOrEmpty()) {
            binding.groupEmpty.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavouritesFragment()
    }
}