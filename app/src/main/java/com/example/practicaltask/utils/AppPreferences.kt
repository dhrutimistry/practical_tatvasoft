package com.example.practicaltask.utils

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferences(var application: Application) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    companion object {
        fun newInstance(application: Application): AppPreferences {
            return AppPreferences(application)
        }
    }

    fun writeSharedPreferencesString(key: String?, value: String?) {
        sharedPreferencesEditor.putString(key, value)
        sharedPreferencesEditor.apply()
    }

    fun writeSharedPreferencesInt(key: String?, value: Int) {
        sharedPreferencesEditor.putInt(key, value)
        sharedPreferencesEditor.apply()
    }

    fun writeSharedPreferencesBool(key: String?, value: Boolean?) {
        sharedPreferencesEditor.putBoolean(key, value!!)
        sharedPreferencesEditor.apply()
    }

    fun clearAllPrefData() {


        sharedPreferencesEditor.apply()
    }

    fun getAppPrefString(key: String?): String? {
        return try {
            sharedPreferences.getString(key, "")
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }

    fun getAppPrefBoolean(key: String?): Boolean {
        return try {
            sharedPreferences.getBoolean(key, false)
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

}