package com.example.milkflow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.R
import com.example.milkflow.adapter.MilkPersonAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentCollectorBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.utils.myToast
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory

class CollectorFragment : Fragment() {

    private var _binding: FragmentCollectorBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MilkPersonAdapter
    private lateinit var viewModel: MilkViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorBinding.inflate(inflater, container, false)

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
            DialogUtils.addPersonDialog(requireContext(), viewModel, "Collector")
        }

        viewModel.getCollectors().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewModel.updateCollectorTotal(it)
        }

        adapter = MilkPersonAdapter(
            onDeletePerson = { person ->
                viewModel.delete(person)
                myToast(
                    requireContext(),
                    "${person.personName} is deleted",
                    R.drawable.baseline_delete_24
                )
            },
            onEditPerson = { person ->
                DialogUtils.editPersonDialog(requireContext(), viewModel, person, "Collector")

            }
        )
        binding.recyclerView.adapter = adapter


        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
