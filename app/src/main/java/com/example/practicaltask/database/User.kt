package com.example.practicaltask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    @ColumnInfo(name = "userGender") val gender: String?,
    @ColumnInfo(name = "userEmail") val email: String?,
    @ColumnInfo(name = "userPhone") val phone: String?,
    @ColumnInfo(name = "userCell") val cell: String?,
    @ColumnInfo(name = "userNat") val nat: String?

)