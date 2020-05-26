package com.ergo.notch.skimmiaproject.ui.music.viewmodel.repositories

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.ergo.notch.skimmiaproject.ui.music.model.AudioModel


class AudioRepository(private val context: Context) {

    fun getAllAudioFromDevice(): List<AudioModel>? {
        val tempAudioList: MutableList<AudioModel> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST
        )
        val cursor: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val path: String = cursor.getString(0)
                val album: String = cursor.getString(1)
                val artist: String = cursor.getString(2)
                val name = path.substring(path.lastIndexOf("/") + 1)
                tempAudioList.add(
                    AudioModel(
                        path,
                        name,
                        album,
                        artist,
                        isPlaying = false
                    )
                )
            }
            cursor.close()
        }
        return tempAudioList
    }

}
