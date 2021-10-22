package com.example.practicaltask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao
{

    @Query("SELECT * FROM movies WHERE genre =:genre")
    fun getAll(genre:String): List<Movies>

    @Insert
    fun insert(users: Movies)


}