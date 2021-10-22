package com.example.practicaltask.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicaltask.R
import com.example.practicaltask.utils.AppConstants
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class CoroutineViewModel(private val context: Application) : ViewModel() {

    //Progress
    val isShowProgress = MutableLiveData(false)
    var strError = MutableLiveData("")
    var booleanAlreadyLoginWithOtherDevice = MutableLiveData(false)

    /**
     *  This method returns true when there is no internet and also shows error message for the same.
     *  @author Ankit Mishra
     *  @param showMessage Provide true if message need to be shown else message will not be shown.(For ex. BaseActivity apis should not show internet error message)
     */

    fun returnTrueIfThereIsNoInternet(showMessage: Boolean = true): Boolean {
        return if (!MyApplication.isInternetAvailable) {
            if (showMessage) {
                strError.value = context.resources.getString(R.string.internet_error)
            }
            true
        } else {
            false
        }
    }


    /**
     * Does General Exception Handling
     * @author Ankit Mishra
     * @param throwable Provide throwable object.
     */
    fun generalExceptionHandling(throwable: Throwable) {
        throwable.let {
            isShowProgress.value = false
            it.printStackTrace()
            when (it) {
                is HttpException -> {
                    try {
                        if (it.code() == AppConstants.INT_UNAUTHORIZED) {
                            booleanAlreadyLoginWithOtherDevice.value = true

                        } else {
                            val mJsonObjectMsg =
                                JSONObject(it.response()!!.errorBody()!!.string())
                            strError.value = mJsonObjectMsg.optString("message")
                        }
                    } catch (e1: IOException) {
                        e1.printStackTrace()
                        strError.value =
                            context.resources.getString(R.string.text_server_error_msg)
                    } catch (e1: JSONException) {
                        e1.printStackTrace()
                        strError.value =
                            context.resources.getString(R.string.text_server_error_msg)
                    }
                }
                is SocketTimeoutException -> {
                    strError.value = context.resources.getString(R.string.text_time_out_msg)
                }
                else -> {
                    strError.value = context.resources.getString(R.string.text_server_error_msg)
                }
            }
        }
    }

    /**
     * Common function to call api using coroutine.
     */
    inline fun <T> callApiUsingCoroutine(
        crossinline apiFunction: suspend () -> T,
        crossinline onSuccess: (result: T) -> Unit,
        showProgress: Boolean = true,
        showProgressButDoNotHideIt: Boolean = false,
        crossinline onThrows: (throwableObject: Throwable) -> Unit = { generalExceptionHandling(it) }
    ) = viewModelScope.launch {

        //Checking if there is internet or not.
        if (!returnTrueIfThereIsNoInternet()) {
            //Retrofit will automatically make suspend functions main-safe so you can call them directly from Dispatchers.Main
            try {
                if (showProgress) isShowProgress.value = true
                val result = apiFunction()
                if (showProgress && !showProgressButDoNotHideIt) isShowProgress.value = false
                onSuccess(result)
            } catch (cause: Throwable) {
                onThrows(cause)
            }
        }
    }


    /**
     * Common function to call multiple serial api using coroutine.(Showing Progress then calling all the apis in parallel then waits for all the apis to complete. After the completion of all the apis, it hides the progress.)
     */
    fun callMultipleSerialApisUsingCoroutine(listOfJobs: List<Job>) {
        //Showing Progress
        isShowProgress.value = true
        println("*******************************************************")
        println("callMultipleSerialApisUsingCoroutine showProgress()")
        println("*******************************************************")

        viewModelScope.launch {
            listOfJobs.joinAll()//Now, the code will be suspended here until all the supplies api function execution completes.

            //Hiding Progress
            isShowProgress.value = false
            println("*******************************************************")
            println("callMultipleSerialApisUsingCoroutine hideProgress()")
            println("*******************************************************")

        }
    }
}