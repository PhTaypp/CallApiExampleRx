package com.example.callapiexamplerx.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.callapiexamplerx.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun startApp(view : View) {
        val intent = Intent(this@MainActivity, ListRepoActivity::class.java)
        startActivity(intent)
    }
}