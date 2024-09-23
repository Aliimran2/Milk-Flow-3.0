package com.example.milkflow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.R
import com.example.milkflow.adapter.ExpenseAdapter
import com.example.milkflow.adapter.MilkPersonAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentExpenseBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory


class ExpenseFragment : Fragment() {

    private var _binding : FragmentExpenseBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ExpenseAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentExpenseBinding.inflate(inflater,container,false)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val repository = MilkRepository(dao, expenseDao)
        val viewModel = ViewModelProvider(this, MilkViewModelFactory(repository))[MilkViewModel::class.java]

        binding.expenseModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.getAllExpenses().observe(viewLifecycleOwner){
            adapter.submitList(it)

            viewModel.updateExpense(it)

        }


        adapter = ExpenseAdapter(
            onDeleteExpense = { item ->
                viewModel.deleteItem(item)
            },
            onEditExpense = { item ->
                Toast.makeText(requireContext(), "${item.itemName} is clicked", Toast.LENGTH_SHORT).show()

            }
        )

        binding.addButton.setOnClickListener {
            DialogUtils.addExpenseDialog(requireContext(),viewModel)
        }

        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}