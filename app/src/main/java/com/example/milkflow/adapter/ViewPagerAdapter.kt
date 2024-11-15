package com.example.milkflow.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.milkflow.ui.CollectorFragment
import com.example.milkflow.ui.ExpenseFragment
import com.example.milkflow.ui.StatFragment
import com.example.milkflow.ui.SupplierFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments =
        listOf(CollectorFragment(), SupplierFragment(), ExpenseFragment(), StatFragment())

    override fun getItemCount(): Int = fragments.size



    override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> SupplierFragment()
                1 -> ExpenseFragment()
                2 -> CollectorFragment()
                else -> StatFragment()
            }
    }
}