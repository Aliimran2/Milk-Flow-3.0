package com.example.milkflow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.R
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentStatBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory


class StatFragment : Fragment() {

    private var _binding : FragmentStatBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStatBinding.inflate(inflater,container,false)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val factory = MilkRepository(dao, expenseDao)
        val viewModel =
            ViewModelProvider(requireActivity(), MilkViewModelFactory(factory))[MilkViewModel::class.java]

        binding.statModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        viewModel.difference.observe(viewLifecycleOwner) { differenceValue ->
//            binding.differenceText.text = differenceValue.toString()
//            Log.d("Difference", "Difference value: $differenceValue")
//        }

        viewModel.getCollectors().observe(viewLifecycleOwner) { collectors ->
            viewModel.updateCollectorTotal(collectors)
            viewModel.calculateDifference()
        }

        viewModel.getSuppliers().observe(viewLifecycleOwner) { suppliers ->
            viewModel.updateTotal(suppliers)
            viewModel.calculateDifference()
        }

        viewModel.getAllExpenses().observe(viewLifecycleOwner) { expenses ->
            viewModel.updateExpenseTotal(expenses)
            viewModel.calculateDifference()
        }





        return binding.root
    }




}