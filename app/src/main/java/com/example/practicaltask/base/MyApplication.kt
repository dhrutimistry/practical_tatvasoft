package com.example.practicaltask.base

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var application: Application
        var isInternetAvailable: Boolean = false
        var mStringVersion: String = ""
        var dialogDefaultButtonClick: MutableLiveData<Int> = MutableLiveData(0)
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            mStringVersion = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }

}
