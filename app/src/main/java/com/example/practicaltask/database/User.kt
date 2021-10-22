package com.example.practicaltask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "thumb_url") val thumb_url: String?
)