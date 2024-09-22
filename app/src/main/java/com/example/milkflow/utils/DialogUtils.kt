package com.example.milkflow.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.milkflow.R
import com.example.milkflow.model.PersonModel
import com.example.milkflow.viewmodel.MilkViewModel

object DialogUtils {

    fun addPersonDialog(context: Context, viewModel: MilkViewModel) {

        val dialogView = LayoutInflater.from(context).inflate(R.layout.add_person_dialog, null)

        val personName = dialogView.findViewById<EditText>(R.id.name_tv)
        val personRateText = dialogView.findViewById<EditText>(R.id.rate_tv)
        val personQuantityText = dialogView.findViewById<EditText>(R.id.quantity_tv)



        AlertDialog.Builder(context)
            .setTitle("Add new person")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->

                    val person = personName.text.toString()
                    val rate = personRateText.text.toString().toIntOrNull() ?: 0
                    val quantity = personQuantityText.text.toString().toIntOrNull() ?: 0

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