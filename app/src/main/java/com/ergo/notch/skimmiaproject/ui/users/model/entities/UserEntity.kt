package com.ergo.notch.skimmiaproject.ui.users.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "lastName")
    val lastName: String,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "photo")
    var photo: String,
    @ColumnInfo(name = "biography")
    val biography: String
) :Serializable{
}