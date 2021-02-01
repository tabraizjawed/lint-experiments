package com.checkstyle.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Configuration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun getWorkManagerConfiguration(): Configuration {
//        TODO("Not yet implemented")
//    }
}
