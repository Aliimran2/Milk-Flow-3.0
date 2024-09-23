package com.example.milkflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.milkflow.model.PersonModel


@Database(entities = [PersonModel::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {

    abstract fun getDao(): MilkPersonDao
    abstract fun getExpenseDao() : ExpenseDao


    companion object {
        @Volatile
       private var INSTANCE: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PersonDatabase::class.java,
                        "milk_person_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}