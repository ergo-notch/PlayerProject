package com.ergo.notch.skimmiaproject.ui.galleryMedia.view.adapters

import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.utils.inflate
import kotlinx.android.synthetic.main.image_item_layout.view.*
import kotlinx.android.synthetic.main.user_item_layout.view.*
import java.io.File


class GalleryMediaAdapter(private val images: List<String>) :
    RecyclerView.Adapter<GalleryMediaAdapter.ViewHolder>() {

    private var listener: MediaGalleryListener? = null

    interface MediaGalleryListener {
        fun onGetImagePath(imagePath: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.image_item_layout))

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileBitmap = BitmapFactory.decodeFile(File(images[position]).absolutePath)
        holder.itemView.ivMEdiaImage.setImageBitmap(fileBitmap)
        holder.itemView.setOnClickListener {
            this.listener?.onGetImagePath(images[position])
        }
    }

    fun setListener(listener: MediaGalleryListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
