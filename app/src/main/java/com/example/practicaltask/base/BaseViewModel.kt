package com.example.practicaltask.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.practicaltask.R
import com.example.practicaltask.database.AppDatabase
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


    var modelResponseMovieData = MutableLiveData<List<ModelResponseMovieData>>(null)
    var booleanLogoutSuccess = MutableLiveData<Boolean>(false)

    //Progress
    var strErrorBase = MutableLiveData<String>("")

    init {
        getMovieData()
    }

    private fun getMovieData() {

        callApiUsingCoroutine(
            apiFunction = { postApi.fetchUserList() },
            onSuccess = {
                modelResponseMovieData.value = it
            },
            onThrows = {
                when (it) {
                    is HttpException -> {
                        try {
                            if (it.code() == AppConstants.INT_UNAUTHORIZED) {
                                val mJsonObjectMsg =
                                    JSONObject(it.response()?.errorBody()!!.string())
                                strErrorBase.value = mJsonObjectMsg.optString("message")

                            } else {
                                val mJsonObjectMsg =
                                    JSONObject(it.response()?.errorBody()!!.string())
                                strErrorBase.value = mJsonObjectMsg.optString("message")
                            }
                        } catch (e1: IOException) {
                            e1.printStackTrace()
                        } catch (e1: JSONException) {
                            e1.printStackTrace()
                        }
                    }
                    is SocketTimeoutException -> {
                        strErrorBase.value = context.resources.getString(R.string.text_time_out_msg)
                    }
                    else -> {
                        strErrorBase.value =
                            context.resources.getString(R.string.text_server_error_msg)
                    }
                }
            })


    }


}