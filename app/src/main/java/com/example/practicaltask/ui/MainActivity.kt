package com.example.practicaltask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.practicaltask.R
import com.example.practicaltask.base.BaseActivity
import com.example.practicaltask.database.Genre
import com.example.practicaltask.databinding.ActivityMainBinding
import com.example.practicaltask.databinding.BaseActivityBinding
import com.example.practicaltask.ui.adapter.MovieListAdapter
import com.example.practicaltask.viewmodel.DashboardViewModel

class MainActivity : BaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val dashboardViewModel: DashboardViewModel  by viewModels()
    lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainBinding = putContentView(R.layout.activity_main) as ActivityMainBinding

        activityMainBinding.dashboardViewModel = dashboardViewModel

        //Setting up the toolbar configuration
        setToolbarConfiguration(false)

        dashboardViewModel.apply {
            modelGenreData.observe(this@MainActivity, Observer {
                if (it != null && it.isNotEmpty()) {
                    activityMainBinding.recyclerviewGenre.adapter = MovieListAdapter(dashboardViewModel,
                        it as ArrayList<Genre>
                    )
                }
            })
        }

    }
}