package com.example.practicaltask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practicaltask.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}