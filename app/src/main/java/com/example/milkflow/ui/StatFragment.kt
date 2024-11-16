package com.example.milkflow.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.milkflow.R
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentStatBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class StatFragment : Fragment(R.layout.fragment_stat) {


    private lateinit var binding: FragmentStatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentStatBinding.bind(view)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val factory = MilkRepository(dao, expenseDao)
        val viewModel =
            ViewModelProvider(
                requireActivity(),
                MilkViewModelFactory(factory)
            )[MilkViewModel::class.java]

        binding.statModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.supplierView.setOnClickListener {
            viewModel.navigateTo(1)
        }

        binding.expenseView.setOnClickListener {
            viewModel.navigateTo(3)
        }

        binding.customerView.setOnClickListener {
            viewModel.navigateTo(2)
        }




        viewModel.getCollectors().observe(viewLifecycleOwner) { collectors ->
            viewModel.updateCustomerTotal(collectors)
            viewModel.calculateDifference()

        }

        viewModel.getSuppliers().observe(viewLifecycleOwner) { suppliers ->
            viewModel.updateSupplierTotal(suppliers)
            viewModel.calculateDifference()
        }

        viewModel.getAllExpenses().observe(viewLifecycleOwner) { expenses ->
            viewModel.updateExpenseTotal(expenses)
            viewModel.calculateDifference()
        }



    }

}