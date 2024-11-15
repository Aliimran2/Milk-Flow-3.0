package com.example.milkflow.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.R
import com.example.milkflow.adapter.ViewPagerAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.ActivityMainBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = PersonDatabase.getInstance(this).getDao()
        val expenseDao = PersonDatabase.getInstance(this).getExpenseDao()
        val factory = MilkRepository(dao, expenseDao)
        val viewModel =
            ViewModelProvider(
                this,
                MilkViewModelFactory(factory)
            )[MilkViewModel::class.java]

        viewModel.navigateToFragment.observe(this){position ->
            binding.viewPager.currentItem = position
        }

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
                    tab.setIcon(R.drawable.stat_icon)
                }
            }
        }.attach()


    }

}