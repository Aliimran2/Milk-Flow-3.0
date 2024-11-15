package com.example.milkflow

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.milkflow.databinding.AddExpenseDialogBinding
import com.example.milkflow.databinding.AddPersonDialogBinding
import com.example.milkflow.databinding.FragmentAddPersonDataBinding
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel
import com.example.milkflow.ui.MainActivity
import com.example.milkflow.viewmodel.MilkViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddPersonDataFragment : BottomSheetDialogFragment(R.layout.fragment_add_person_data) {

    private var personType: String? = null
    private var dataType: String? = null


    private lateinit var binding: FragmentAddPersonDataBinding
    private val viewModel: MilkViewModel by activityViewModels()

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPersonDataBinding.bind(view)


        personType = arguments?.getString("personType")
        dataType = arguments?.getString("dataType")

        adjustFieldVisibility()
        adjustHintText()


        binding.saveBtn.setOnClickListener {
            if (dataType != null) {
                addData(dataType!!)
            } else {
                Toast.makeText(context, "Data type is missing", Toast.LENGTH_LONG).show()
            }
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
    }


    private fun adjustHintText() {
        when (dataType) {
            "Expense" -> {
                // Set hint texts for Expense
                binding.nameIpl.hint = "Enter item name"
                binding.rateIpl.hint = "Enter expense amount"
                binding.quantityIpl.visibility = View.GONE // Hide quantity field for Expense
            }
            "Person" -> {
                // Set hint texts for Person
                binding.nameIpl.hint = "Enter person's name"
                binding.rateIpl.hint = "Enter rate"
                binding.quantityIpl.hint = "Enter quantity"
            }
        }
    }

    private fun adjustFieldVisibility() {
        when (dataType) {
            "Expense" -> {
                // Show name and rate fields; hide quantity field
                binding.nameTv.visibility = View.VISIBLE
                binding.rateTv.visibility = View.VISIBLE
                binding.quantityTv.visibility = View.GONE
            }
            "Person" -> {
                // Show all fields for person
                binding.nameTv.visibility = View.VISIBLE
                binding.rateTv.visibility = View.VISIBLE
                binding.quantityTv.visibility = View.VISIBLE
            }
            else -> {
                // Hide all fields as fallback
                binding.nameTv.visibility = View.GONE
                binding.rateTv.visibility = View.GONE
                binding.quantityTv.visibility = View.GONE
            }
        }
    }


    private fun addData(dataType: String) {
        when (dataType) {
            "Expense" -> {
                val itemName = binding.nameTv.text.toString()
                val expense = binding.rateTv.text.toString().toDoubleOrNull() ?: 0.0
                binding.quantityTv.visibility = View.GONE

                if (itemName.isNotEmpty() && expense != 0.0) {
                    val expenseModel = ExpenseModel(itemName = itemName, itemAmount = expense)
                    viewModel.insertItem(expenseModel)
                    Toast.makeText(context, "Expense added successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else {
                    Toast.makeText(context, "Please fill all fields for Expense", Toast.LENGTH_LONG)
                        .show()
                }
            }

            "Person" -> {
                val person = binding.nameTv.text.toString()
                val rate = binding.rateTv.text.toString().toDoubleOrNull() ?: 0.0
                val quantity = binding.quantityTv.text.toString().toDoubleOrNull() ?: 0.0

                if (person.isNotEmpty() && rate != 0.0 && quantity != 0.0 && personType != null) {
                    val personModel = PersonModel(
                        personName = person,
                        personRate = rate,
                        personQuantity = quantity,
                        personType = personType!!
                    )
                    viewModel.insert(personModel)
                    Toast.makeText(context, "Person added successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else {
                    Toast.makeText(context, "Please fill all fields for Person", Toast.LENGTH_LONG)
                        .show()
                }
            }

            else -> {
                Toast.makeText(context, "Unknown data type", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance(personType: String?, dataType: String): AddPersonDataFragment {
            val fragment = AddPersonDataFragment()
            val args = Bundle().apply {
                putString("personType", personType)
                putString("dataType", dataType)
            }
            fragment.arguments = args
            return fragment
        }
    }
}




