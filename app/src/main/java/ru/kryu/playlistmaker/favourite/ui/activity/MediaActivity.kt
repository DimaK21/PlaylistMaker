package ru.kryu.playlistmaker.favourite.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import ru.kryu.playlistmaker.R
import ru.kryu.playlistmaker.databinding.ActivityMediaBinding
import ru.kryu.playlistmaker.favourite.ui.fragments.MediaViewPagerAdapter

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.favourites)
                1 -> tab.text = getString(R.string.playlists)
            }
        }
        tabMediator.attach()

        binding.btnBackMedia.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}