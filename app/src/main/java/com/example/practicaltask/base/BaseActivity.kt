package com.example.practicaltask.base

import android.app.Application
import android.app.Dialog
import android.content.*
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.practicaltask.R
import com.example.practicaltask.database.AppDatabase
import com.example.practicaltask.databinding.BaseActivityBinding
import com.example.practicaltask.utils.listeners.setSafeOnClickListener
import com.example.practicaltask.utils.listners.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    @Inject
    lateinit var applications: Application

    @Inject
    lateinit var context: Context

    private lateinit var baseActivityBinding: BaseActivityBinding


    val baseViewModel: BaseViewModel by viewModels()

    private lateinit var broadcastReceiver: BroadcastReceiver

    private var dialogExitApplication: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        baseActivityBinding = DataBindingUtil.setContentView(this, R.layout.base_activity)

        broadcastReceiver = ConnectivityReceiver()

        registerReceiver(
            broadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        baseViewModel.strErrorBase.observe(this, Observer {
            if (it != "") {
//                showMessage(it)
            }
        })

    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)

    }



    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.base_activity_toolbar)

        baseActivityBinding.baseActivityToolbarImageviewBack.setSafeOnClickListener {
            onBackPressed()
        }

    }

    protected open fun <T : ViewDataBinding?> putContentView(@LayoutRes resId: Int): T {
        val frameLayout = findViewById<ViewGroup?>(R.id.base_activity_fl_content)

        return DataBindingUtil.inflate<T>(
            layoutInflater,
            resId,
            frameLayout,
            true
        )
    }



    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        MyApplication.isInternetAvailable = isConnected
    }



    fun showExitDialog() {
        val mAlertDialogBuilder = AlertDialog.Builder(this)

        mAlertDialogBuilder.setTitle(resources.getString(R.string.text_exit_title))
            .setMessage(resources.getString(R.string.text_exit_information))
            .setCancelable(false)
            .setPositiveButton(resources.getString(R.string.text_yes)) { dialog, _ ->
                dialog.dismiss()
                this.finishAffinity()
            }
            .setNegativeButton(resources.getString(R.string.text_no)) { dialog, _ ->
                dialog.dismiss()
            }

        if (dialogExitApplication == null) {
            dialogExitApplication = mAlertDialogBuilder.create()
        }

        if (!dialogExitApplication!!.isShowing) {
            dialogExitApplication!!.show()
        }
    }

    //Desc: This method sets up the toolbar according to the provided configuration.
    open fun setToolbarConfiguration(
        isToolbarVisible: Boolean,
        toolbarConfiguration: ToolbarConfiguration? = null
    ) {
        //First, initiating the toolbar
        initToolbar()
        //Now, Setting up the configuration
        if (isToolbarVisible) {
            baseActivityBinding.apply {
                baseActivityToolbar.isVisible = true
                toolbarConfiguration?.let { configuration ->
                    //Setting title
                    baseActivityToolbarTextviewTitle.text = configuration.title

                    //Setting up visibility configuration
                    baseActivityToolbarTextviewTitle.isVisible = configuration.isTitleVisible
                    baseActivityToolbarImageviewBack.isVisible = configuration.isBackButtonVisible
                }
            }

        } else {
            baseActivityBinding.baseActivityToolbar.isVisible = false
        }
    }



    //Desc: Date class used for configuring toolbar properties.
    data class ToolbarConfiguration(
        val title: String = "",
        val isTitleVisible: Boolean = true,//generally title is visible
        val isBackButtonVisible: Boolean = false,//generally back button is visible
    )


}