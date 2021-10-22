package com.example.practicaltask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao
{

    @Query("SELECT * FROM movies")
    fun getAll(): List<Movies>

    @Query("SELECT * FROM movies")
    fun getAllWithFlow(): Flow<List<Movies>>

    @Insert
    fun insertAll(movies: ArrayList<Movies>)

    @Insert
    fun insert(users: Movies)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllUsersAsynchronously(movies: ArrayList<Movies>)

    @Query("DELETE FROM movies")
    fun removeAll()

    @Query("DELETE FROM movies")
    suspend fun removeAllUsersAsynchronously()
}