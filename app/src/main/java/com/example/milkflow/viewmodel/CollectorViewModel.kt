package com.example.milkflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.milkflow.model.PersonModel
import com.example.milkflow.repository.MilkRepository
import kotlinx.coroutines.launch

class CollectorViewModel(private val repository: MilkRepository):ViewModel() {

    private val _totalAmount =MutableLiveData<Int>()
    val totalAmount :LiveData<Int>
        get() = _totalAmount

    private val _noOfCollectors = MutableLiveData<Int>()
    val noOfSuppliers : LiveData<Int> get() = _noOfCollectors

    private val _totalQuantity = MutableLiveData<Int>()
    val totalQuantity : LiveData<Int> get() = _totalQuantity

    fun getCollectors() : LiveData<List<PersonModel>> = repository.getPersonsByType("Collector")

    fun insert(personModel: PersonModel) = viewModelScope.launch{
        repository.insert(personModel)
    }

    fun delete(personModel: PersonModel) = viewModelScope.launch {
        repository.delete(personModel)
    }

    fun update(personModel: PersonModel) = viewModelScope.launch {
        repository.update(personModel)
    }

    fun updateTotal(persons : List<PersonModel>){

        _totalAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _noOfCollectors.value = persons.size
        _totalQuantity.value = persons.sumOf { it.personQuantity }
    }








}

class CollectorVMFactory(private val repository: MilkRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilkViewModel(repository) as T
    }
}