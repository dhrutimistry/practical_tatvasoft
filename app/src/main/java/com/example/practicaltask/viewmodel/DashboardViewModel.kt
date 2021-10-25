package com.example.practicaltask.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.example.practicaltask.R
import com.example.practicaltask.base.CoroutineViewModel
import com.example.practicaltask.base.MyApplication
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.database.Genre
import com.example.practicaltask.database.Movies
import com.example.practicaltask.network.PostApi
import com.example.practicaltask.network.model.ModelResponseMovieData
import com.example.practicaltask.ui.adapter.GenreListAdapter
import com.example.practicaltask.utils.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
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

    var isDataAvailable = ObservableBoolean(true)
    var modelResponseMovieData = MutableLiveData<List<ModelResponseMovieData>>(null)
    var modelGenreData = MutableLiveData<List<Genre>>(null)
    var strErrorBase = MutableLiveData<String>("")
    var list   = ArrayList<Genre>()

    //Adapter
    var genreListAdapter: GenreListAdapter = GenreListAdapter(context,this,list,appDatabase)



    fun apiCall(){
        if (MyApplication.isInternetAvailable) {
            if(appDatabase.genreDao().getAll().isEmpty()) {
                getMovieData()
            }else{
                list.addAll(appDatabase.genreDao().getAll())
                genreListAdapter.notifyDataSetChanged();
            }

            Log.d("my_data", appDatabase.genreDao().getAll().toString())
            Log.d("my_data2", appDatabase.moviesDao().getAll("Drama").toString())
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
                    var actors:String =""
                    var directors:String =""
                    var genres:String =""

                    for (j in 0 until it[i].actors.size) {
                        actors = if (actors == "") {
                            it[i].actors[j]
                        }else{
                            "${actors}, ${it[i].actors[j]}"
                        }

                    }

                    for (j in 0 until it[i].directors.size) {
                        directors = if (directors == "") {
                            it[i].directors[j]
                        }else{
                            "${directors}, ${it[i].directors[j]}"
                        }

                    }

                    for (j in 0 until it[i].genre.size) {
                        genres = if (genres == "") {
                            it[i].genre[j]
                        }else{
                            "${genres}, ${it[i].genre[j]}"
                        }

                    }
                    for (j in 0 until  it[i].genre.size) {

                        appDatabase.moviesDao().insert(Movies(0,it[i].name,it[i].thumb_url,it[i].genre[j],genres,
                        it[i].rating,it[i].desc,actors,directors))
                        appDatabase.genreDao().insert(Genre(0, it[i].genre[j]))
                    }

                }
                Log.d("my_data", appDatabase.genreDao().getAll().toString())
                Log.d("my_data2", appDatabase.moviesDao().getAll("Action").toString())

                list.addAll(appDatabase.genreDao().getAll())
                genreListAdapter.notifyDataSetChanged();

                isDataAvailable.set(true)

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