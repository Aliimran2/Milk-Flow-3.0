package com.example.milkflow.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.milkflow.databinding.AddExpenseDialogBinding
import com.example.milkflow.databinding.AddPersonDialogBinding
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel
import com.example.milkflow.viewmodel.MilkViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtils {

    fun addPersonDialog(context: Context, viewModel: MilkViewModel, personType: String) {

        val inflater = LayoutInflater.from(context)
        val binding = AddPersonDialogBinding.inflate(inflater)

        AlertDialog.Builder(context)
            .setTitle("Add new person")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->

                val person = binding.nameTv.text.toString()
                val rate = binding.rateTv.text.toString().toIntOrNull() ?: 0
                val quantity = binding.quantityTv.text.toString().toIntOrNull() ?: 0

                if (person.isNotEmpty() && rate != 0 && quantity != 0) {
                    val personModel = PersonModel(
                        personName = person,
                        personRate = rate,
                        personQuantity = quantity,
                        personType = personType
                    )
                    viewModel.insert(personModel)
                    Toast.makeText(context, "Data added successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun addExpenseDialog(context: Context, viewModel: MilkViewModel) {

        val inflater = LayoutInflater.from(context)
        val binding = AddExpenseDialogBinding.inflate(inflater)

        AlertDialog.Builder(context)
            .setTitle("Add mew expense")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->

                val itemName = binding.itemNameTv.text.toString()
                val expense = binding.itemAmount.text.toString().toIntOrNull() ?: 0

                if (itemName.isNotEmpty() && expense != 0 ) {
                    val expenseModel = ExpenseModel(itemName = itemName, itemAmount = expense)
                    viewModel.insertItem(expenseModel)
                    Toast.makeText(context, "Data added successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun editPersonDialog(
        context: Context,
        viewModel: MilkViewModel,
        personModel: PersonModel,
        personType: String
    ) {

        val inflater = LayoutInflater.from(context)
        val binding = AddPersonDialogBinding.inflate(inflater)

        binding.nameTv.setText(personModel.personName)
        binding.rateTv.setText(personModel.personRate.toString())
        binding.quantityTv.setText(personModel.personQuantity.toString())

        AlertDialog.Builder(context)
            .setTitle("Edit the person")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->
                val person = binding.nameTv.text.toString()
                val rate = binding.rateTv.text.toString().toIntOrNull() ?: 0
                val quantity = binding.quantityTv.text.toString().toIntOrNull() ?: 0

                if (person.isNotEmpty() && rate != 0 && quantity != 0) {
                    val updatedPersonModel = PersonModel(
                        id = personModel.id,
                        personName = person,
                        personRate = rate,
                        personQuantity = quantity,
                        personType = personType
                    )
                    viewModel.update(updatedPersonModel)
                    Toast.makeText(context, "Edited successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }








    fun editExpenseDialog(
        context: Context,
        viewModel: MilkViewModel,
        expenseModel: ExpenseModel,

    ) {

        val inflater = LayoutInflater.from(context)
        val binding = AddExpenseDialogBinding.inflate(inflater)

        binding.itemNameTv.setText(expenseModel.itemName)
        binding.itemAmount.setText(expenseModel.itemAmount)


        AlertDialog.Builder(context)
            .setTitle("Edit Item")
            .setView(binding.root)
            .setCancelable(false)
            .setPositiveButton("Save") { _, _ ->
                val item = binding.itemNameTv.text.toString()
                val amount = binding.itemAmount.text.toString().toIntOrNull() ?: 0


                if (item.isNotEmpty() && amount != 0 ) {
                    val updatedExpense = ExpenseModel(
                        id = expenseModel.id,
                        itemName = item,
                        itemAmount = amount,
                    )
                    viewModel.updateItem(updatedExpense)
                    Toast.makeText(context, "Edited successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}