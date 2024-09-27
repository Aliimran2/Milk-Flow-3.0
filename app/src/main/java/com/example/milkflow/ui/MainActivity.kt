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
                    tab.setText(R.string.suppliers)
                    tab.setIcon(R.drawable.icons_supplier)

                }
                1 -> {
                    tab.setText(R.string.expenses)
                    tab.setIcon(R.drawable.icons_expenses)
                }
                2 -> {
                    tab.setText(R.string.customers)
                    tab.setIcon(R.drawable.icons_collector)
                }
                else -> {
                    tab.setText(R.string.stat)
                    tab.setIcon(R.drawable.icon_stat)
                }
            }
        }.attach()


    }

}