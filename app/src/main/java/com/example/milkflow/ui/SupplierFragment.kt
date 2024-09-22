package com.example.milkflow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.milkflow.adapter.MilkPersonAdapter
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentSupplierBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory


class SupplierFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MilkPersonAdapter
    private var _binding: FragmentSupplierBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSupplierBinding.inflate(inflater, container, false)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val factory = MilkRepository(dao)
        val viewModel =
            ViewModelProvider(this, MilkViewModelFactory(factory))[MilkViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addButton.setOnClickListener {
            DialogUtils.addPersonDialog(requireContext(), viewModel, "Supplier")
        }

        viewModel.getSuppliers().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewModel.updateTotal(it)
//             binding.totalSumSupplierTv.text= "Total: ${viewModel.totalAmount}"
        }

        recyclerView = binding.recyclerView
        adapter = MilkPersonAdapter(onDeletePerson = { person ->
            viewModel.delete(person)
            Toast.makeText(requireContext(), "${person.personName} is deleted", Toast.LENGTH_SHORT).show()
        }, onEditPerson = { person ->
            DialogUtils.editPersonDialog(requireContext(), viewModel, person, "Supplier")
        }
        )
        recyclerView.adapter = adapter

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}