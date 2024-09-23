package com.example.milkflow.repository

import androidx.lifecycle.LiveData
import com.example.milkflow.database.ExpenseDao
import com.example.milkflow.database.MilkPersonDao
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel

class MilkRepository(private val dao: MilkPersonDao, private val expenseDao: ExpenseDao) {

    fun getPersonsByType(type:String) : LiveData<List<PersonModel>> = dao.getPersonsByType(type)
    fun getAllExpense():LiveData<List<ExpenseModel>> = expenseDao.getAllExpenditures()

    suspend fun insert(person:PersonModel){
        dao.insert(person)
    }

    suspend fun delete(person: PersonModel){
        dao.delete(person)
    }

    suspend fun update(person: PersonModel){
        dao.update(person)
    }

    suspend fun insertExpense(expenseModel: ExpenseModel) {
        expenseDao.insertExpense(expenseModel)
    }

    suspend fun updateExpense(expenseModel: ExpenseModel) {
        expenseDao.updateExpense(expenseModel)
    }

    suspend fun  deleteExpense(expenseModel: ExpenseModel) {
        expenseDao.deleteExpense(expenseModel)
    }

}