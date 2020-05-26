package com.ergo.notch.skimmiaproject.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.base.views.BaseFragment
import com.ergo.notch.skimmiaproject.ui.galleryMedia.view.fragments.GalleryMediaFragment
import com.ergo.notch.skimmiaproject.ui.music.view.AudioPlayerFragment
import com.ergo.notch.skimmiaproject.ui.users.view.fragments.UserListFragment
import kotlinx.android.synthetic.main.fragment_main_layout.*

class MainFragment : BaseFragment() {

    override fun getResourceLayout(): Int = R.layout.fragment_main_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        this.configureMenu()
    }

    private fun configureMenu() {
        this.navigation.setOnNavigationItemSelectedListener { menuItem ->
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.galleyMedia -> GalleryMediaFragment()
                R.id.musicPlayer -> AudioPlayerFragment()
                else -> UserListFragment()
            }
            this@MainFragment.replaceChildFragment(
                fragment,
                R.id.frame_container,
                this@MainFragment.childFragmentManager,
                false
            )
            true
        }
        this.navigation.selectedItemId = R.id.galleyMedia
    }

    companion object {
        fun newInstance() = MainFragment()
    }


}
