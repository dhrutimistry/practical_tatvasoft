package com.example.practicaltask.utils.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class ModelConverters : Serializable {


//    @TypeConverter
//    fun fromModelName(optionValues: ModelResponseUserName?): String? {
//        if (optionValues == null) {
//            return null
//        }
//
//        val gsonModelPicture = Gson()
//        val type = object : TypeToken<ModelResponseUserName?>() {}.type
//        return gsonModelPicture.toJson(optionValues, type)
//    }
//
//    @TypeConverter
//    fun toModelName(optionValuesString: String?): ModelResponseUserName? {
//        if (optionValuesString == null) {
//            return null
//        }
//
//        val gsonModelPicture = Gson()
//        val type = object : TypeToken<ModelResponseUserName?>() {}.type
//        return gsonModelPicture.fromJson<ModelResponseUserName>(optionValuesString, type)
//    }

}