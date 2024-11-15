package com.example.milkflow.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.AddPersonDataFragment
import com.example.milkflow.R
import com.example.milkflow.adapter.ExpenseAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentExpenseBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory
import com.google.android.material.snackbar.Snackbar


class ExpenseFragment : Fragment(R.layout.fragment_expense) {

    private lateinit var binding: FragmentExpenseBinding
    private lateinit var adapter: ExpenseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExpenseBinding.bind(view)
        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val repository = MilkRepository(dao, expenseDao)
        val viewModel = ViewModelProvider(
            requireActivity(),
            MilkViewModelFactory(repository)
        )[MilkViewModel::class.java]

        binding.expenseModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.getAllExpenses().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewModel.updateExpenseTotal(it)

        }


        adapter = ExpenseAdapter(
            onDeleteExpense = { item ->
                viewModel.deleteItem(item)
                Snackbar.make(view, "${item.itemName} is Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { viewModel.insertItem(item) }
                    .show()
            },
            onEditExpense = { item ->
                DialogUtils.editExpenseDialog(requireContext(), viewModel, item)

            }
        )

        binding.addButton.setOnClickListener {

            val fragment = AddPersonDataFragment.newInstance("", "Expense")
            val supportFragmentManager = (activity as MainActivity).supportFragmentManager
            fragment.show(supportFragmentManager, AddPersonDataFragment::class.java.simpleName)




        }

        binding.recyclerView.adapter = adapter
    }
}