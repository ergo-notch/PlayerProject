package com.ergo.notch.skimmiaproject.ui.music.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ergo.notch.skimmiaproject.ui.galleryMedia.viewmodel.repositories.MediaRepository
import com.ergo.notch.skimmiaproject.ui.music.model.AudioModel
import com.ergo.notch.skimmiaproject.ui.music.viewmodel.repositories.AudioRepository
import com.ergo.notch.skimmiaproject.ui.users.viewmodel.repositories.UserRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AudioPlayerViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {

    private var context: Application = application
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    private var mediaLiveData: MutableLiveData<List<AudioModel>> = MutableLiveData()
    fun audioList(): MutableLiveData<List<AudioModel>> {
        return mediaLiveData
    }

    fun getAllAudioFiles() {
        launch(Dispatchers.Main) {
            mediaLiveData.value = withContext(Dispatchers.IO) {
                AudioRepository(context).getAllAudioFromDevice()
            }
        }
    }


}