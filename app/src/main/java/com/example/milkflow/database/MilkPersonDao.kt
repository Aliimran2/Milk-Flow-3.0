package com.example.milkflow.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.milkflow.model.PersonModel

@Dao
interface MilkPersonDao {
    @Query("SELECT * FROM person_table ORDER BY personName ASC")
    fun getAll(): LiveData<List<PersonModel>>

    @Insert
    suspend fun insert(personModel: PersonModel)

    @Delete
    suspend fun delete(personModel: PersonModel)

    @Update
    suspend fun update(personModel: PersonModel)


}