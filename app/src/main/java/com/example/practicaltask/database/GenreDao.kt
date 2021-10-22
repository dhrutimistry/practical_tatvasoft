package com.example.practicaltask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre ORDER BY genreName ASC")
    fun getAll(): List<Genre>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(genre:Genre)

    @Query("DELETE FROM genre")
    fun removeAll()

}