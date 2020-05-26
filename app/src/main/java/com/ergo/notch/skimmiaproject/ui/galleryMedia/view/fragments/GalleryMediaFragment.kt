package com.ergo.notch.skimmiaproject.ui.galleryMedia.view.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.base.views.BaseFragment
import com.ergo.notch.skimmiaproject.ui.galleryMedia.view.adapters.GalleryMediaAdapter
import com.ergo.notch.skimmiaproject.ui.galleryMedia.viewmodel.MediaViewModel
import com.greentoad.turtlebody.imagepreview.ImagePreview
import com.greentoad.turtlebody.imagepreview.core.ImagePreviewConfig
import kotlinx.android.synthetic.main.fragment_gallery_media_layout.*
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class GalleryMediaFragment : BaseFragment() {

    private var callback: GalleryMediaCallback? = null
    private var isAddingNewUser: Boolean = false
    private lateinit var mediaViewModel: MediaViewModel

    override fun getResourceLayout(): Int = R.layout.fragment_gallery_media_layout

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(view: View, savedInstanceState: Bundle?) {
        mediaViewModel = ViewModelProvider(this).get(MediaViewModel::class.java)
//        mediaViewModel.getAllImages()
        this.checkPermissions()
        this.observeResults()
        this.loadArguments()
    }

    private fun loadArguments() {
        this.arguments?.let {
            this.isAddingNewUser = it.getBoolean(ADDING_NEW_USER)
            this.callback = it.getSerializable(GALLERY_MEDIA_CALLBACK) as GalleryMediaCallback
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            this.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            this.mediaViewModel.getAllImages()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            this.checkPermissions()
        }
    }

    private fun observeResults() {
        this.mediaViewModel.getImageList().observe(this, Observer { images ->
            images?.let {
                this.loadAdapter(it)
            }
        })
    }

    private fun loadAdapter(images: List<String>) {
        val adapter = GalleryMediaAdapter(images)
        this.rvImages.adapter = adapter
        this.rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter.setListener(object : GalleryMediaAdapter.MediaGalleryListener {
            override fun onGetImagePath(imagePath: String) {
                if (this@GalleryMediaFragment.isAddingNewUser) {
                    this@GalleryMediaFragment.returnView(imagePath)
                } else {
                    this@GalleryMediaFragment.showPreview(imagePath)
                }
            }
        })
    }

    private fun returnView(imagePath: String) {
        this.callback?.onSelectUserImage(imagePath)
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun showPreview(imagePath: String) {
        val config = ImagePreviewConfig().setAllowAddButton(true)
            .setUris(arrayListOf(Uri.fromFile(File(imagePath))))
        ImagePreview.with(this.requireActivity())
            .setConfig(config)
            .start()
    }

    companion object {
        private const val REQUEST_READ_EXTERNAL_STORAGE = 1001
        private const val ADDING_NEW_USER = "adding.new.user"
        private const val GALLERY_MEDIA_CALLBACK = "gallery.media.callback"

        fun newInstance(isAddingNewUser: Boolean, selectUserImage: GalleryMediaCallback) =
            GalleryMediaFragment().apply {
                arguments = bundleOf(
                    ADDING_NEW_USER to isAddingNewUser,
                    GALLERY_MEDIA_CALLBACK to selectUserImage

                )
            }


    }

}
