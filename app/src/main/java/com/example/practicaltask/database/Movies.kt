package com.example.practicaltask.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movies(
    @PrimaryKey(autoGenerate = true) val uid: Int,

    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "thumb_url") val thumb_url: String?,
    @ColumnInfo(name = "genre") val genre: String?,
    @ColumnInfo(name = "genres") val genres: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "actors") val actors: String?,
    @ColumnInfo(name = "directors") val directors: String?
)