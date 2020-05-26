package com.ergo.notch.skimmiaproject.ui.users.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.lifecycle.viewModelScope
import com.ergo.notch.skimmiaproject.persistence.UserDataBase
import com.ergo.notch.skimmiaproject.ui.users.model.entities.UserEntity
import com.ergo.notch.skimmiaproject.ui.users.viewmodel.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
//    val allUsers: LiveData<List<UserEntity>>

    init {
        val wordsDao = UserDataBase.getDatabase(application).userDao()
        repository = UserRepository(wordsDao)
    }

    fun getAllUsers(): LiveData<List<UserEntity>> = repository.getAllUsers

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun update(user: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(user)
    }
}