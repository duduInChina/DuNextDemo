package com.dudu.module_run

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dudu.module_run.BuildConfig
import com.therouter.TheRouter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TheRouter.build(BuildConfig.ROUTER_URL).navigation()
    }
}