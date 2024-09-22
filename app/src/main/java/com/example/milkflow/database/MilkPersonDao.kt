package com.example.milkflow.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.milkflow.model.PersonModel

@Dao
interface MilkPersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personModel: PersonModel)

    @Delete
    suspend fun delete(personModel: PersonModel)

    @Update
    suspend fun update(personModel: PersonModel)

    @Query("SELECT * FROM person_table WHERE personType = :type")
    fun getPersonsByType(type:String):LiveData<List<PersonModel>>


}