package com.example.milkflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel
import com.example.milkflow.repository.MilkRepository
import kotlinx.coroutines.launch

class MilkViewModel(private val repository: MilkRepository) : ViewModel() {

    fun getSuppliers(): LiveData<List<PersonModel>> = repository.getPersonsByType("Supplier")
    fun getCollectors(): LiveData<List<PersonModel>> = repository.getPersonsByType("Collector")
    fun getAllExpenses(): LiveData<List<ExpenseModel>> = repository.getAllExpense()

    private val _totalAmount = MutableLiveData<Int>()
    val totalAmount: LiveData<Int>
        get() = _totalAmount

    private val _totalCollectorAmount = MutableLiveData<Int>()
    val totalCollectorAmount: LiveData<Int>
        get() = _totalCollectorAmount

    private val _totalExpenditure = MutableLiveData<Int>()
    val totalExpenditure: LiveData<Int>
        get() = _totalExpenditure

    private val _difference = MutableLiveData<Int>()
    val difference: LiveData<Int>
        get() = _difference

    private val _noOfSuppliers = MutableLiveData<Int>()
    val noOfSuppliers: LiveData<Int> get() = _noOfSuppliers

    private val _totalQuantity = MutableLiveData<Int>()
    val totalQuantity: LiveData<Int> get() = _totalQuantity

    fun insert(personModel: PersonModel) = viewModelScope.launch {
        repository.insert(personModel)
    }

    fun delete(personModel: PersonModel) = viewModelScope.launch {
        repository.delete(personModel)
    }

    fun update(personModel: PersonModel) = viewModelScope.launch {
        repository.update(personModel)
    }

    fun insertItem(expenseModel: ExpenseModel) = viewModelScope.launch {
        repository.insertExpense(expenseModel)
    }

    fun deleteItem(expenseModel: ExpenseModel) = viewModelScope.launch {
        repository.deleteExpense(expenseModel)
    }

    fun updateItem(expenseModel: ExpenseModel) = viewModelScope.launch {
        repository.updateExpense(expenseModel)
    }

    fun updateTotal(persons: List<PersonModel>) {

        _totalAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _noOfSuppliers.value = persons.size
        _totalQuantity.value = persons.sumOf { it.personQuantity }
    }

    fun updateCollectorTotal(persons: List<PersonModel>) {
        _totalCollectorAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _noOfSuppliers.value = persons.size
        _totalQuantity.value = persons.sumOf { it.personQuantity }
    }


    fun updateExpenseTotal(expense: List<ExpenseModel>) {
        _totalExpenditure.value = expense.sumOf { it.itemAmount }
    }

    fun calculateDifference() {
        val diff = (_totalCollectorAmount.value ?: 0) + (_totalAmount.value
            ?: 0) - (_totalExpenditure.value ?: 0)
        _difference.value = diff

    }



}

class MilkViewModelFactory(private val repository: MilkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilkViewModel(repository) as T
    }
}