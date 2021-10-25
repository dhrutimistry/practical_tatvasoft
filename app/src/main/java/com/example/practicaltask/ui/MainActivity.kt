package com.example.practicaltask.ui

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.practicaltask.R
import com.example.practicaltask.base.BaseActivity
import com.example.practicaltask.base.MyApplication
import com.example.practicaltask.database.Genre
import com.example.practicaltask.databinding.ActivityMainBinding
import com.example.practicaltask.ui.adapter.GenreListAdapter
import com.example.practicaltask.utils.listners.ConnectivityReceiver
import com.example.practicaltask.viewmodel.DashboardViewModel

class MainActivity : BaseActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val dashboardViewModel: DashboardViewModel  by viewModels()
    internal lateinit var genreListAdapter: GenreListAdapter
    private val movieList = ArrayList<Genre>()
    private lateinit var broadcastReceiver: BroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = putContentView(R.layout.activity_main) as ActivityMainBinding

        activityMainBinding.dashboardViewModel = dashboardViewModel

        broadcastReceiver = ConnectivityReceiver()

        registerReceiver(
            broadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

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

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        showExitDialog()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        MyApplication.isInternetAvailable = isConnected
        dashboardViewModel.apiCall()

    }
}