package com.example.practicaltask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "genre",indices = [Index(value = ["genreName"], unique = true)])
data class Genre (
    @PrimaryKey(autoGenerate = true) val gid: Int,
    @ColumnInfo(name = "genreName") val genreName: String?,
)