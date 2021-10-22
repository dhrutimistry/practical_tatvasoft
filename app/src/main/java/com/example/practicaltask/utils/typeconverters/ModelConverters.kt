package com.example.practicaltask.utils.typeconverters

import androidx.room.TypeConverter
import com.example.practicaltask.database.Movies
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class ModelConverters : Serializable {


    @TypeConverter
    fun fromModelMovie(optionValues: Movies?): String? {
        if (optionValues == null) {
            return null
        }

        val gsonModelPicture = Gson()
        val type = object : TypeToken<Movies?>() {}.type
        return gsonModelPicture.toJson(optionValues, type)
    }

    @TypeConverter
    fun toModelMovie(optionValuesString: String?): Movies? {
        if (optionValuesString == null) {
            return null
        }

        val gsonModelPicture = Gson()
        val type = object : TypeToken<Movies?>() {}.type
        return gsonModelPicture.fromJson<Movies>(optionValuesString, type)
    }

}