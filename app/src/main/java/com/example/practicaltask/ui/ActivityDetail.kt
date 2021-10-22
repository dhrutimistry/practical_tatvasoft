package com.example.practicaltask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.practicaltask.R
import com.example.practicaltask.base.BaseActivity
import com.example.practicaltask.databinding.ActivityDetailBinding
import com.example.practicaltask.databinding.ActivityMainBinding
import com.example.practicaltask.viewmodel.DashboardViewModel

class ActivityDetail : BaseActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    var img:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activityDetailBinding = putContentView(R.layout.activity_detail) as ActivityDetailBinding

        activityDetailBinding.dashboardViewModel = dashboardViewModel

        //Setting up the toolbar configuration
        setToolbarConfiguration(true, ToolbarConfiguration(isBackButtonVisible = true))
        img = intent.extras!!.getString("img").toString()


        activityDetailBinding.name.text = intent.extras!!.getString("name").toString()
        activityDetailBinding.starring.text = "Starring : ${intent.extras!!.getString("actor").toString()}"
        activityDetailBinding.director.text = "Director : ${intent.extras!!.getString("director").toString()}"
        activityDetailBinding.rating.text = "Ratings : ${intent.extras!!.getString("rating").toString()}"
        activityDetailBinding.desc.text = intent.extras!!.getString("desc").toString()
        activityDetailBinding.genre.text = "Genre : ${intent.extras!!.getString("genre").toString()}"

        Glide.with(this)
            .load(img)
            .into(activityDetailBinding.image)
    }
}