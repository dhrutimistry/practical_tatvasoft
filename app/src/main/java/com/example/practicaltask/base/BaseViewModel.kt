package com.example.practicaltask.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.practicaltask.R
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.network.PostApi
import com.example.practicaltask.utils.AppConstants
import com.example.practicaltask.utils.AppConstants.Companion.REQUEST_CONTENT_TYPE_JSON
import com.example.practicaltask.utils.AppPreferences
import com.kotlin.mvvm.structure.base.MyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    val context: Application
) : CoroutineViewModel(context) {



    //Progress
    var strErrorBase = MutableLiveData<String>("")



}