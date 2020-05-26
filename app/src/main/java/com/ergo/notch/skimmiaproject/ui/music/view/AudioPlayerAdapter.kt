package com.ergo.notch.skimmiaproject.ui.music.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.ui.music.model.AudioModel
import com.ergo.notch.skimmiaproject.utils.gone
import com.ergo.notch.skimmiaproject.utils.inflate
import com.ergo.notch.skimmiaproject.utils.visible
import kotlinx.android.synthetic.main.item_audio_file_layout.view.*

class AudioPlayerAdapter(private val audioFiles: List<AudioModel>) :
    RecyclerView.Adapter<AudioPlayerAdapter.ViewHolder>() {
    private var listener: AudioPlayerListener? = null

    private var itemSelected = -1

    fun selectAudio(position: Int) {
        itemSelected = position
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setListener(listener: AudioPlayerListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_audio_file_layout))

    override fun getItemCount(): Int = audioFiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val audioFile = this.audioFiles[position]
        holder.itemView.tvName.text = audioFile.mName
        holder.itemView.ivPauseAudio.setOnClickListener {
            this.listener?.onPauseAudio(audioFile,position)
        }
        holder.itemView.ivPlayAudio.setOnClickListener {
            this.listener?.onPlayAudio(audioFile,position)
        }

        if (itemSelected == position) {
            holder.itemView.ivPauseAudio.visible()
            holder.itemView.ivPlayAudio.gone()
        } else {
            holder.itemView.ivPauseAudio.gone()
            holder.itemView.ivPlayAudio.visible()
        }
    }

}

interface AudioPlayerListener {
    fun onPauseAudio(audio: AudioModel,position: Int)
    fun onPlayAudio(audio: AudioModel,position: Int)
}
