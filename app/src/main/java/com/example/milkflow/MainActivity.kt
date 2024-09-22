package com.example.milkflow

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.milkflow.adapter.MilkPersonAdapter
import com.example.milkflow.dataProvider.DataProvider
import com.example.milkflow.database.PersonDatabase
import com.example.milkflow.databinding.ActivityMainBinding
import com.example.milkflow.model.PersonModel
import com.example.milkflow.repository.MilkRepository
import com.example.milkflow.utils.DialogUtils
import com.example.milkflow.utils.SumAndDiffUtils
import com.example.milkflow.viewmodel.MilkViewModel
import com.example.milkflow.viewmodel.MilkViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MilkPersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = PersonDatabase.getInstance(this).getDao()
        val factory = MilkRepository(dao)
        val viewModel = ViewModelProvider(this,MilkViewModelFactory(factory))[MilkViewModel::class.java]



        binding.fab.setOnClickListener {
            DialogUtils.addPersonDialog(this,viewModel)
        }

        viewModel.getAll().observe(this){
            adapter.submitList(it)
             binding.textView.text= SumAndDiffUtils.updateTotal(it).toString()
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = MilkPersonAdapter(
            onDeletePerson = {person ->
                viewModel.delete(person)
            },
            onEditPerson = {person->
            }
        )
            recyclerView.adapter = adapter

    }

}