package com.example.milkflow.repository

import androidx.lifecycle.LiveData
import com.example.milkflow.database.MilkPersonDao
import com.example.milkflow.model.PersonModel

class MilkRepository(val dao: MilkPersonDao) {

    fun getAll() : LiveData<List<PersonModel>> = dao.getAll()

    suspend fun insert(person:PersonModel){
        dao.insert(person)
    }

    suspend fun delete(person: PersonModel){
        dao.delete(person)
    }

    suspend fun update(person: PersonModel){
        dao.update(person)
    }

}