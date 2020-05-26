package com.ergo.notch.skimmiaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ergo.notch.skimmiaproject.ui.main.view.MainFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .commit()
    }
}
