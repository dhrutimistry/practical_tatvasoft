package com.example.practicaltask.network

import com.example.practicaltask.network.model.ModelResponseMovieData
import com.example.practicaltask.utils.AppConstants
import retrofit2.Call
import retrofit2.http.GET


/**
 * The interface which provides methods to get result of webservices
 */

interface PostApi {


    @GET(AppConstants.URL_FETCH_MOVIE_LIST)
    suspend fun fetchUserList(): List<ModelResponseMovieData>
}