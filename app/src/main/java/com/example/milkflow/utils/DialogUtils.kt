package com.example.milkflow.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.milkflow.databinding.AddPersonDialogBinding
import com.example.milkflow.model.PersonModel
import com.example.milkflow.viewmodel.MilkViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {

    fun addPersonDialog(context: Context, viewModel: MilkViewModel) {

        val inflater = LayoutInflater.from(context)
        val binding = AddPersonDialogBinding.inflate(inflater)

        MaterialAlertDialogBuilder(context)
            .setTitle("Add new person")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->

                    val person = binding.nameTv.text.toString()
                    val rate = binding.rateTv.text.toString().toIntOrNull() ?: 0
                    val quantity = binding.quantityTv.text.toString().toIntOrNull() ?: 0

                if (person.isNotEmpty() && rate !=0 && quantity != 0){
                    val personModel = PersonModel(
                        personName = person,
                        personRate = rate,
                        personQuantity = quantity
                    )
                    viewModel.insert(personModel)
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}