package com.example.milkflow.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.FragmentStatBinding
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class StatFragment : Fragment() {


    private var _binding: FragmentStatBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatBinding.inflate(inflater, container, false)

        val dao = PersonDatabase.getInstance(requireContext()).getDao()
        val expenseDao = PersonDatabase.getInstance(requireContext()).getExpenseDao()
        val factory = MilkRepository(dao, expenseDao)
        val viewModel =
            ViewModelProvider(
                requireActivity(),
                MilkViewModelFactory(factory)
            )[MilkViewModel::class.java]

        binding.statModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.pieEntriesLiveData.observe(viewLifecycleOwner) {
            val dataSet = PieDataSet(it, "")
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            dataSet.valueTextSize = 12f
            dataSet.valueTextColor = Color.WHITE


            val pieData = PieData(dataSet)
            pieData.setValueFormatter(object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "${value.toInt()}%"
                }
            })

            binding.pieChart.apply {

                setUsePercentValues(true)
                description.isEnabled = false
                setDrawEntryLabels(true)
                holeRadius = 58f
                transparentCircleRadius = 61f
                setHoleColor(Color.WHITE)
                setCenterTextColor(Color.BLACK)
                setCenterTextSize(22f)
                centerText = "Summary"
                isRotationEnabled = true
                setTouchEnabled(true)
                animateY(3000, com.github.mikephil.charting.animation.Easing.EaseInOutQuad)

                legend.apply {
                    verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    orientation = Legend.LegendOrientation.HORIZONTAL
                    form = Legend.LegendForm.SQUARE
                    formSize = 10f
                    textSize = 12f
                    textColor = Color.BLACK
                    isWordWrapEnabled = true
                }
                data = pieData
                invalidate()
            }
        }

        viewModel.getCollectors().observe(viewLifecycleOwner) { collectors ->
            viewModel.updateCustomerTotal(collectors)
            viewModel.calculateDifference()

        }

        viewModel.getSuppliers().observe(viewLifecycleOwner) { suppliers ->
            viewModel.updateSupplierTotal(suppliers)
            viewModel.calculateDifference()
        }

        viewModel.getAllExpenses().observe(viewLifecycleOwner) { expenses ->
            viewModel.updateExpenseTotal(expenses)
            viewModel.calculateDifference()
        }





        return binding.root
    }

}