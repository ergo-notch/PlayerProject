package com.ergo.notch.skimmiaproject.ui.galleryMedia.view.fragments

import java.io.Serializable

interface GalleryMediaCallback:Serializable {
    fun onSelectUserImage(imagePath:String)
}
