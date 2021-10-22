package com.example.practicaltask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao
{
    //For Getting the list of user for only 1 time.
    //************************************************
    @Query("SELECT * FROM user")
    fun getAll(): List<User>
    //************************************************

    //For Getting the list of user for each time there is update in the user list.
    //************************************************
    @Query("SELECT * FROM user")
    fun getAllWithFlow(): Flow<List<User>>
    //************************************************

    //For Inserting the list of user for synchronously.
    //************************************************
    @Insert
    fun insertAll(users: ArrayList<User>)

    @Insert
    fun insert(users: User)

    //For Inserting the list of user for asynchronously using coroutine.
    //************************************************
    @Insert(onConflict = REPLACE)
    suspend fun insertAllUsersAsynchronously(users: ArrayList<User>)

    @Query("DELETE FROM user")
    fun removeAll()

    //For Deleting the list of user for asynchronously using coroutine.
    //************************************************
    @Query("DELETE FROM user")
    suspend fun removeAllUsersAsynchronously()
}