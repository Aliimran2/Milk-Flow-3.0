package com.example.milkflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel
import com.example.milkflow.repository.MilkRepository
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch

class MilkViewModel(private val repository: MilkRepository) : ViewModel() {

    private val _navigateToFragment = MutableLiveData<Int>()
    val navigateToFragment : LiveData<Int> get() = _navigateToFragment

    fun navigateTo(position : Int) {
        _navigateToFragment.value = position
    }



    fun getSuppliers(): LiveData<List<PersonModel>> = repository.getPersonsByType("Supplier")
    fun getCollectors(): LiveData<List<PersonModel>> = repository.getPersonsByType("Collector")
    fun getAllExpenses(): LiveData<List<ExpenseModel>> = repository.getAllExpense()

    private val _totalSupplierAmount = MutableLiveData<Double>()
    val totalSupplierAmount: LiveData<Double> get() = _totalSupplierAmount

    private val _totalCustomerAmount = MutableLiveData<Double>()
    val totalCustomerAmount: LiveData<Double> get() = _totalCustomerAmount

    private val _totalExpenditure = MutableLiveData<Double>()
    val totalExpenditure: LiveData<Double> get() = _totalExpenditure

    private val _difference = MutableLiveData<Double>()
    val difference: LiveData<Double> get() = _difference

    private val _noOfSuppliers = MutableLiveData<Int>()
    val noOfSuppliers: LiveData<Int> get() = _noOfSuppliers

    private val _noOfCollectors = MutableLiveData<Int>()
    val noOfCollectors: LiveData<Int> get() = _noOfCollectors

    private val _totalSupplierQuantity = MutableLiveData<Double>()
    val totalSupplierQuantity: LiveData<Double> get() = _totalSupplierQuantity

    private val _totalCustomerQuantity = MutableLiveData<Double>()
    val totalCustomerQuantity: LiveData<Double> get() = _totalCustomerQuantity

    val pieEntriesLiveData = MediatorLiveData<List<PieEntry>>()
    init {
        pieEntriesLiveData.addSource(_totalSupplierAmount){updateEntries()}
        pieEntriesLiveData.addSource(_totalCustomerAmount){updateEntries()}
        pieEntriesLiveData.addSource(_totalExpenditure){updateEntries()}
        pieEntriesLiveData.addSource(_difference){updateEntries()}
    }

    private fun updateEntries(){
        val supplier = _totalSupplierAmount.value?:0
        val collector = _totalCustomerAmount.value?:0
        val expenses = _totalExpenditure.value?:0
        val profitOrLoss = _difference.value?:0

        val pieEntries = listOf(
            PieEntry(supplier.toFloat(), "Supplier"),
            PieEntry(collector.toFloat(), "Customer"),
            PieEntry(expenses.toFloat(), "Expense"),
            PieEntry(profitOrLoss.toFloat(), "Profit")
        )

        pieEntriesLiveData.value = pieEntries
    }





    fun insert(personModel: PersonModel) = viewModelScope.launch { repository.insert(personModel) }

    fun delete(personModel: PersonModel) = viewModelScope.launch { repository.delete(personModel) }

    fun update(personModel: PersonModel) = viewModelScope.launch { repository.update(personModel) }

    fun insertItem(expenseModel: ExpenseModel) = viewModelScope.launch { repository.insertExpense(expenseModel) }

    fun deleteItem(expenseModel: ExpenseModel) = viewModelScope.launch { repository.deleteExpense(expenseModel) }

    fun updateItem(expenseModel: ExpenseModel) = viewModelScope.launch { repository.updateExpense(expenseModel) }

    fun updateSupplierTotal(persons: List<PersonModel>) {

        _totalSupplierAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _totalSupplierQuantity.value = persons.sumOf { it.personQuantity }
        _noOfSuppliers.value = persons.size
    }

    fun updateCustomerTotal(persons: List<PersonModel>) {
        _totalCustomerAmount.value = persons.sumOf { it.personRate * it.personQuantity }
        _totalCustomerQuantity.value = persons.sumOf { it.personQuantity }
        _noOfCollectors.value = persons.size
    }


    fun updateExpenseTotal(expense: List<ExpenseModel>) {
        _totalExpenditure.value = expense.sumOf { it.itemAmount }
    }

    fun calculateDifference() {
        val diff = (_totalCustomerAmount.value ?: 0.0) - ((_totalExpenditure.value ?: 0.0) + (_totalSupplierAmount.value ?: 0.0))
        _difference.value = diff

    }
}


class MilkViewModelFactory(private val repository: MilkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MilkViewModel(repository) as T
    }
}