package com.example.practicaltask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practicaltask.utils.typeconverters.ModelConverters

@Database(entities = [Movies::class,Genre::class], version = 1)
@TypeConverters(ModelConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun genreDao(): GenreDao
//    abstract fun genreMovieDao(): GenreMovieDao
}