package com.example.practicaltask.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.practicaltask.R
import com.example.practicaltask.base.BaseActivity
import com.example.practicaltask.database.Genre
import com.example.practicaltask.databinding.ActivityMainBinding
import com.example.practicaltask.ui.adapter.GenreListAdapter
import com.example.practicaltask.viewmodel.DashboardViewModel

class MainActivity : BaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val dashboardViewModel: DashboardViewModel  by viewModels()
    internal lateinit var genreListAdapter: GenreListAdapter
    private val movieList = ArrayList<Genre>()
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = putContentView(R.layout.activity_main) as ActivityMainBinding

        activityMainBinding.dashboardViewModel = dashboardViewModel

        //Setting up the toolbar configuration
        setToolbarConfiguration(false)
//        movieListAdapter = MovieListAdapter(movieList)
//        val layoutManager = LinearLayoutManager(applicationContext)
//        activityMainBinding.recyclerviewGenre.layoutManager = layoutManager
//        activityMainBinding.recyclerviewGenre.adapter = movieListAdapter

        dashboardViewModel.apply {
            isShowProgress.observe(this@MainActivity, Observer {
                it ?.let {
                    if (it) {
                        showProgress()
                    } else {
                        hideProgress()
                    }
                }
            })
        }

    }
}