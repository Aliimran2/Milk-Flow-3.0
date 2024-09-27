package com.example.milkflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.milkflow.R
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel
import com.example.milkflow.repository.MilkRepository
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch

class MilkViewModel(private val repository: MilkRepository) : ViewModel() {

    fun getSuppliers(): LiveData<List<PersonModel>> = repository.getPersonsByType("Supplier")
    fun getCollectors(): LiveData<List<PersonModel>> = repository.getPersonsByType("Collector")
    fun getAllExpenses(): LiveData<List<ExpenseModel>> = repository.getAllExpense()

    private val _totalSupplierAmount = MutableLiveData<Int>()
    val totalSupplierAmount: LiveData<Int> get() = _totalSupplierAmount

    private val _totalCollectorAmount = MutableLiveData<Int>()
    val totalCollectorAmount: LiveData<Int> get() = _totalCollectorAmount

    private val _totalExpenditure = MutableLiveData<Int>()
    val totalExpenditure: LiveData<Int> get() = _totalExpenditure

    private val _difference = MutableLiveData<Int>()
    val difference: LiveData<Int> get() = _difference

    private val _noOfSuppliers = MutableLiveData<Int>()
    val noOfSuppliers: LiveData<Int> get() = _noOfSuppliers

    private val _noOfCollectors = MutableLiveData<Int>()
    val noOfCollectors: LiveData<Int> get() = _noOfCollectors

    private val _totalSupplierQuantity = MutableLiveData<Int>()
    val totalSupplierQuantity: LiveData<Int> get() = _totalSupplierQuantity

    private val _totalCollectorQuantity = MutableLiveData<Int>()
    val totalCollectorQuantity: LiveData<Int> get() = _totalCollectorQuantity

    val pieEntriesLiveData = MediatorLiveData<List<PieEntry>>()
    init {
        pieEntriesLiveData.addSource(_totalSupplierQuantity){updateEntries()}
        pieEntriesLiveData.addSource(_totalCollectorQuantity){updateEntries()}
        pieEntriesLiveData.addSource(_totalExpenditure){updateEntries()}
        pieEntriesLiveData.addSource(_difference){updateEntries()}
    }

    private fun updateEntries(){
        val supplier = _totalSupplierAmount.value?:0
        val collector = _totalCollectorQuantity.value?:0
        val expenses = _totalExpenditure.value?:0
        val profitOrLoss = _difference.value?:0

        val pieEntries = listOf(
            PieEntry(supplier.toFloat(), R.string.suppliers),
            PieEntry(collector.toFloat(), R.string.customers),
            PieEntry(expenses.toFloat(), R.string.expenses),
            PieEntry(profitOrLoss.toFloat(), R.string.stat)
        )

        pieEntriesLiveData.value = pieEntries
    }



    fun insert(personModel: PersonModel) = viewModelScope.launch { repository.insert(personModel) }

    fun delete(personModel: PersonModel) = viewModelScope.launch { repository.delete(personModel) }

    fun update(personModel: PersonModel) = viewModelScope.launch { repository.update(personModel) }

    fun insertItem(expenseModel: ExpenseModel) = viewModelScope.launch { repository.insertExpense(expenseModel) }

    fun deleteItem(expenseModel: ExpenseModel) = viewModelScope.launch { repository.deleteExpense(expenseModel) }

    fun updateItem(expenseModel: ExpenseModel) = viewModelScope.launch { repository.updateExpense(expenseModel) }

    fun updateTotal(persons: List<PersonModel>) {

        _totalSupplierAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _totalSupplierQuantity.value = persons.sumOf { it.personQuantity }
        _noOfSuppliers.value = persons.size
    }

    fun updateCollectorTotal(persons: List<PersonModel>) {
        _totalCollectorAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _totalCollectorQuantity.value = persons.sumOf { it.personQuantity }
        _noOfCollectors.value = persons.size
    }


    fun updateExpenseTotal(expense: List<ExpenseModel>) {
        _totalExpenditure.value = expense.sumOf { it.itemAmount }
    }

    fun calculateDifference() {
        val diff = (_totalCollectorAmount.value ?: 0) - ((_totalExpenditure.value
            ?: 0) + (_totalSupplierAmount.value
            ?: 0))
        _difference.value = diff

    }
}

class MilkViewModelFactory(private val repository: MilkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilkViewModel(repository) as T
    }
}