package com.ergo.notch.skimmiaproject.ui.users.model.domain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ergo.notch.skimmiaproject.persistence.BaseDao
import com.ergo.notch.skimmiaproject.ui.users.model.entities.UserEntity


@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    @Query("SELECT * from users_table ORDER BY id ASC")
    abstract fun getAllUsers(): LiveData<List<UserEntity>>

}