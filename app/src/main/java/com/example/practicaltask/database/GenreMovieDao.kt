package com.example.practicaltask.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreMovieDao
{
    @Query("SELECT * FROM user INNER JOIN genremovie ON user.uid = genremovie.userId")
    fun getAll(): List<GenreMovie>

//    @Insert (onConflict = OnConflictStrategy.REPLACE)
//    fun insert(genre:GenreMovie)

    @Query("DELETE FROM genremovie")
    fun removeAll()

    @Insert
    fun insert( userRepoJoin:GenreMovie)
//
//    @Query("SELECT * FROM user INNER JOIN user_repo_join ON
//            user.id=user_repo_join.userId WHERE
//            user_repo_join.repoId=:repoId")
//            List<User> getUsersForRepository(final int repoId);
//
//    @Query("SELECT * FROM repo INNER JOIN user_repo_join ON
//            repo.id=user_repo_join.repoId WHERE
//            user_repo_join.userId=:userId")
//            List<Repo> getRepositoriesForUsers(final int userId);
}