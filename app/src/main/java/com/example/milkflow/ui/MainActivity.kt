package com.example.milkflow.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.milkflow.R
import com.example.milkflow.adapter.ViewPagerAdapter
import com.example.milkflow.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this)


        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            when (position){
                0 -> {
                    tab.text = "Inflow"
                    tab.setIcon(R.drawable.icons_supplier)

                }
                1 -> {
                    tab.text = "Expenses"
                    tab.setIcon(R.drawable.icons_expenses)
                }
                2 -> {
                    tab.text = "Outflow"
                    tab.setIcon(R.drawable.icons_collector)
                }
                else -> {
                    tab.text = "Stats"
                    tab.setIcon(R.drawable.icons_statistics_b)
                }
            }
        }.attach()


    }

}