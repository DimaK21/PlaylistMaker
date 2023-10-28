package ru.kryu.playlistmaker.playlists.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kryu.playlistmaker.favourite.ui.fragment.FavouritesFragment

class MediaViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavouritesFragment.newInstance()
            else -> PlaylistsFragment.newInstance()
        }
    }
}