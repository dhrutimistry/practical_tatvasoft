package com.example.practicaltask.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.practicaltask.R
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.database.Genre
import com.example.practicaltask.network.PostApi
import com.example.practicaltask.network.model.ModelResponseMovieData
import com.example.practicaltask.utils.AppConstants
import com.example.practicaltask.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    val postApi: PostApi,
    val context: Application,
    val appPreferences: AppPreferences,
    val appDatabase: AppDatabase,
) : CoroutineViewModel(context) {

    var strErrorBase = MutableLiveData<String>("")




}