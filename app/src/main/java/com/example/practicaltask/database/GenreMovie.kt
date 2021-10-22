package com.example.practicaltask.database

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "genremovie",
    primaryKeys = ["gid", "uid"],
    foreignKeys = [ForeignKey(
        entity = Movies::class,
        parentColumns = ["id"],
        childColumns = ["uid"]
    ), ForeignKey(entity = Genre::class, parentColumns = ["id"], childColumns = ["gid"])]
)
class GenreMovie(val userId: Int, val repoId: Int)