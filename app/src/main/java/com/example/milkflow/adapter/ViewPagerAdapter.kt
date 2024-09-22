package com.example.milkflow.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.milkflow.CollectorFragment
import com.example.milkflow.ExpenseFragment
import com.example.milkflow.R
import com.example.milkflow.StatFragment
import com.example.milkflow.SupplierFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments =
        listOf(CollectorFragment(), SupplierFragment(), ExpenseFragment(), StatFragment())

    override fun getItemCount(): Int = fragments.size



    override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> SupplierFragment()
                1 -> CollectorFragment()
                2 -> ExpenseFragment()
                else -> StatFragment()
            }
    }
}