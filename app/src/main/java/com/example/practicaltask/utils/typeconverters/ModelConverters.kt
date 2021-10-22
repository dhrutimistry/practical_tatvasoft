package com.example.practicaltask.utils.typeconverters

import androidx.room.TypeConverter
import com.example.practicaltask.database.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class ModelConverters : Serializable {


    @TypeConverter
    fun fromModelMovie(optionValues: User?): String? {
        if (optionValues == null) {
            return null
        }

        val gsonModelPicture = Gson()
        val type = object : TypeToken<User?>() {}.type
        return gsonModelPicture.toJson(optionValues, type)
    }

    @TypeConverter
    fun toModelMovie(optionValuesString: String?): User? {
        if (optionValuesString == null) {
            return null
        }

        val gsonModelPicture = Gson()
        val type = object : TypeToken<User?>() {}.type
        return gsonModelPicture.fromJson<User>(optionValuesString, type)
    }

}