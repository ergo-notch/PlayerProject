package com.ergo.notch.skimmiaproject.ui.users.viewmodel.repositories

import androidx.lifecycle.LiveData
import com.ergo.notch.skimmiaproject.ui.users.model.domain.UserDao
import com.ergo.notch.skimmiaproject.ui.users.model.entities.UserEntity

class UserRepository(private val userDao: UserDao) {

    val getAllUsers: LiveData<List<UserEntity>> = userDao.getAllUsers()

    suspend fun insert(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    suspend fun update(userEntity: UserEntity) {
        userDao.update(userEntity)
    }

}