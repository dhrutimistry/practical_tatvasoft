package com.example.practicaltask.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.practicaltask.R
import com.example.practicaltask.base.CoroutineViewModel
import com.example.practicaltask.base.MyApplication
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.database.Genre
import com.example.practicaltask.database.User
import com.example.practicaltask.network.PostApi
import com.example.practicaltask.network.model.ModelResponseMovieData
import com.example.practicaltask.ui.adapter.MovieListAdapter
import com.example.practicaltask.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject constructor(
    private val postApi: PostApi,
    private val context: Application,
    private val appDatabase: AppDatabase
) : CoroutineViewModel(context) {

//    var strTextViewMessage = MutableLiveData(context.resources.getString(R.string.text_no_data))
    var isDataAvailable = ObservableBoolean(true)
    var modelResponseMovieData = MutableLiveData<List<ModelResponseMovieData>>(null)
    var modelGenreData = MutableLiveData<List<Genre>>(null)
    var strErrorBase = MutableLiveData<String>("")

    var list   = ArrayList<User>()

    //Adapter
    lateinit var movieListAdapter: MovieListAdapter

    var arrayListOfUser = appDatabase.userDao().getAllWithFlow().asLiveData()

    init {

        if (MyApplication.isInternetAvailable) {
            getMovieData()
        }
    }

    /**
     * Description : Web Service call to fetch list of movies.
     */
    private fun getMovieData() {

        callApiUsingCoroutine(
            apiFunction = { postApi.fetchUserList() },
            onSuccess = {
                modelResponseMovieData.value = it
                appDatabase.genreDao().removeAll()
                for (i in it.indices){
                    for (j in 0 until  it[i].genre.size) {
//                        for (k in it.indices){
//                            list.add( User(0,it[k].name,it[k].thumb_url))
//                        }
                        appDatabase.genreDao().insert(Genre(0, it[i].genre[j]
                            ))
                    }

                }
//                movieListAdapter = MovieListAdapter(appDatabase.genreDao().getAll())
                Log.d("my_data", appDatabase.genreDao().getAll().toString())

                modelGenreData.value = appDatabase.genreDao().getAll()
//                movieListAdapter.submitList(appDatabase.genreDao().getAll())
//                movieListAdapter = MovieListAdapter(this,
//                    appDatabase.genreDao().getAll() as ArrayList<Genre>
//                )

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