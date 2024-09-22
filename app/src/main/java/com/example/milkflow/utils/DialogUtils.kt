package com.example.milkflow.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.milkflow.R
import com.example.milkflow.databinding.AddPersonDialogBinding
import com.example.milkflow.model.PersonModel
import com.example.milkflow.viewmodel.MilkViewModel

object DialogUtils {

    fun addPersonDialog(context: Context, viewModel: MilkViewModel) {

        val inflater = LayoutInflater.from(context)
        val binding = AddPersonDialogBinding.inflate(inflater)

        AlertDialog.Builder(context)
            .setTitle("Add new person")
            .setView(binding.root)
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