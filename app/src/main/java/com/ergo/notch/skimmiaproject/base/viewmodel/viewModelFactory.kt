package com.ergo.notch.skimmiaproject.base.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ergo.notch.skimmiaproject.ui.users.viewmodel.UserViewModel

object viewModelFactory : ViewModelProvider.Factory {
    lateinit var app: Application

    fun setApplication(application: Application) {
        app = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(app) as T
    }

}