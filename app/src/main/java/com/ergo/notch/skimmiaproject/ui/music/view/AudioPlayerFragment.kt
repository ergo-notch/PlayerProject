package com.ergo.notch.skimmiaproject.ui.music.view

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.ergo.notch.skimmiaproject.R
import com.ergo.notch.skimmiaproject.base.views.BaseFragment
import com.ergo.notch.skimmiaproject.ui.music.model.AudioModel
import com.ergo.notch.skimmiaproject.ui.music.viewmodel.AudioPlayerViewModel
import kotlinx.android.synthetic.main.fragment_audio_player_layout.*

/**
 * A simple [Fragment] subclass.
 */
class AudioPlayerFragment : BaseFragment() {

    private var adapter: AudioPlayerAdapter? = null
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioViewModel: AudioPlayerViewModel
    private var isFirstPlaying = false

    override fun getResourceLayout(): Int = R.layout.fragment_audio_player_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        this.audioViewModel = ViewModelProvider(this).get(AudioPlayerViewModel::class.java)
        this.audioViewModel.getAllAudioFiles()
        this.observerResults()
    }

    private fun observerResults() {
        this.audioViewModel.audioList().observe(this, Observer { audioFiles ->
            if (!audioFiles.isNullOrEmpty()) {
                this.loadAdapter(audioFiles)
            }
        })
    }

    private fun loadAdapter(audioFiles: List<AudioModel>) {
        this.adapter = AudioPlayerAdapter(audioFiles)
        this.rvAudioFiles.adapter = adapter
        this.rvAudioFiles.layoutManager = LinearLayoutManager(requireContext())
        adapter?.setListener(object : AudioPlayerListener {
            override fun onPauseAudio(audio: AudioModel, position: Int) {
                pauseFile()
                adapter?.selectAudio(position)
                clearSelection()
            }

            override fun onPlayAudio(audio: AudioModel, position: Int) {
                playFile(audio)
                adapter?.selectAudio(position)
            }
        })


    }

    private fun clearSelection() {
        this.adapter?.selectAudio(-1)
    }

    private fun playFile(audio: AudioModel) {
        if (this.isFirstPlaying) {
            this.mediaPlayer.stop()
        } else {
            this.isFirstPlaying = true
        }
        this.mediaPlayer =
            MediaPlayer.create(
                requireContext(),
                Uri.parse(audio.mPath)
            )
        this.mediaPlayer.isLooping = true
        this.mediaPlayer.start()

    }

    private fun pauseFile() {
        this.mediaPlayer.pause()
    }


}
