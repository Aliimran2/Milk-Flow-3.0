package com.example.milkflow.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.AddPersonDataFragment
import com.example.milkflow.R
import com.example.milkflow.adapter.MilkPersonAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentCollectorBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CollectorFragment : Fragment(R.layout.fragment_collector) {

    private lateinit var binding : FragmentCollectorBinding
    private lateinit var adapter: MilkPersonAdapter
    private lateinit var viewModel: MilkViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCollectorBinding.bind(view)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val factory = MilkRepository(dao, expenseDao)
        viewModel = ViewModelProvider(
                requireActivity(),
                MilkViewModelFactory(factory)
            )[MilkViewModel::class.java]

        binding.collectoVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addButton.setOnClickListener {


            val supportFragmentManager = (activity as MainActivity).supportFragmentManager
            val fragment = AddPersonDataFragment.newInstance("Collector", "Person")
            fragment.show(supportFragmentManager, AddPersonDataFragment::class.java.simpleName)

        }

        viewModel.getCollectors().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewModel.updateCustomerTotal(it)
        }



        adapter = MilkPersonAdapter(
            isSupplier = false,
            onDeletePerson = { person ->
                viewModel.delete(person)
                Snackbar.make(view, "${person.personName} is deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo"){viewModel.insert(person)}
                    .show()
            },
            onEditPerson = { person ->
                DialogUtils.editPersonDialog(requireContext(), viewModel, person, "Collector")

            }
        )

        binding.recyclerView.adapter = adapter

    }
}
