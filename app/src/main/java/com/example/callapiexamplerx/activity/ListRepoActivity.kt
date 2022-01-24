package com.example.callapiexamplerx.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.callapiexamplerx.R
import com.example.callapiexamplerx.fragment.ListRepoFragment
import com.example.callapiexamplerx.fragment.ListUserCoroutineFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope

class ListRepoActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var fragment: Fragment
    lateinit var prLoading : ProgressBar
    var scope = CoroutineScope(CoroutineName("myScope"))
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_repo)
        prLoading = findViewById(R.id.progress_loading)
        tabLayout = findViewById(R.id.tabLayout)

        fragment = ListRepoFragment()
        tabLayout.getTabAt(1)?.select()
        val fm: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = fm.beginTransaction()
        fragment.let { ft.replace(R.id.listDeviceContent, it) }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.selected_tab))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
//        val mode = intent.extras?.getString(Constant.MODE).toString()
        setupEventTabLayout()
    }

    private fun setupEventTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        goHome()
                    }

                    1 -> {
                        fragment = ListRepoFragment()
                    }

                    2 -> {
                        fragment = ListUserCoroutineFragment()
                    }
                }
                val fm: FragmentManager = supportFragmentManager
                val ft: FragmentTransaction = fm.beginTransaction()
                fragment.let { ft.replace(R.id.listDeviceContent, it) }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
    /*
       back to main screen
     */
    private fun  goHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        finishAffinity()
    }
    fun showLoading(){
        prLoading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        prLoading.visibility = View.GONE
    }
}