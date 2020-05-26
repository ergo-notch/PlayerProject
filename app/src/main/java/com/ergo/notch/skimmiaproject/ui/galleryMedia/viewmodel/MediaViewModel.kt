package com.ergo.notch.skimmiaproject.ui.galleryMedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ergo.notch.skimmiaproject.ui.galleryMedia.viewmodel.repositories.MediaRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MediaViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private var context: Application = application
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    private var imagesLiveData: MutableLiveData<List<String>> = MutableLiveData()
    fun getImageList(): MutableLiveData<List<String>> {
        return imagesLiveData
    }

    fun getAllImages() {
        launch(Dispatchers.Main) {
            imagesLiveData.value = withContext(Dispatchers.IO) {
                MediaRepository().loadImagesfromSDCard(context)
            }
        }
    }



}