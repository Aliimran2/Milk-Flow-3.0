package com.example.milkflow.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.milkflow.AddPersonDataFragment
import com.example.milkflow.R
import com.example.milkflow.adapter.MilkPersonAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentSupplierBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class SupplierFragment : Fragment(R.layout.fragment_supplier) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MilkPersonAdapter
    private lateinit var binding: FragmentSupplierBinding
    private lateinit var viewModel: MilkViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSupplierBinding.bind(view)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val factory = MilkRepository(dao, expenseDao)
        viewModel =
            ViewModelProvider(requireActivity(), MilkViewModelFactory(factory))[MilkViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addButton.setOnClickListener {


            val supportFragmentManager = (activity as MainActivity).supportFragmentManager
            val fragment = AddPersonDataFragment.newInstance("Supplier", "Person")
            fragment.show(supportFragmentManager, AddPersonDataFragment::class.java.simpleName)

        }

        viewModel.getSuppliers().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewModel.updateSupplierTotal(it)
        }

        recyclerView = binding.recyclerView
        adapter = MilkPersonAdapter(
            isSupplier = true,
            onDeletePerson = { person ->

                val deleteConfirm = AlertDialog.Builder(requireContext())
                deleteConfirm.setTitle("Delete Record")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes"){_, _ ->
                        viewModel.delete(person)
                    Snackbar.make(view, "${person.personName} is deleted",Snackbar.LENGTH_LONG)
                        .setAction("Undo") { viewModel.insert(person) }
                        .show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()

            }, onEditPerson = { person ->
                DialogUtils.editPersonDialog(requireContext(), viewModel, person, "Supplier")
            }
        )
        recyclerView.adapter = adapter



    }



}