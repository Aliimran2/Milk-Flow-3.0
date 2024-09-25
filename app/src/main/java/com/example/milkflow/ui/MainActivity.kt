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
                    tab.text = "Suppliers"
                    tab.setIcon(R.drawable.supplier_text_icon)

                }
                1 -> {
                    tab.text = "Expenses"
                    tab.setIcon(R.drawable.expense_text_icon)
                }
                2 -> {
                    tab.text = "Collectors"
                    tab.setIcon(R.drawable.collectors_text_icon)
                }
                else -> {
                    tab.text = "Stats"
                    tab.setIcon(R.drawable.icon_stat)
                }
            }
        }.attach()


    }

}