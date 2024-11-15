package com.example.milkflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.milkflow.model.ExpenseModel
import com.example.milkflow.model.PersonModel


@Database(entities = [PersonModel::class, ExpenseModel::class], version = 2, exportSchema = false)
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
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE!!
        }

        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create new table
                db.execSQL("""
                CREATE TABLE person_table_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                personName TEXT NOT NULL,
                personRate REAL NOT NULL,
                personQuantity REAL NOT NULL,
                personType TEXT NOT NULL
                )
            """.trimIndent())
                // copy schema from the previous table into new table
                db.execSQL("""
                INSERT INTO person_table_new(id, personName, personRate, personQuantity, personType)
                SELECT id, personName, personRate, personQuantity, personType FROM person_table
            """.trimIndent())

                // drop previous table and rename new table
                db.execSQL("DROP TABLE person_table")
                db.execSQL("ALTER TABLE person_table_new RENAME TO person_table")


                db.execSQL("""
                CREATE TABLE expense_table_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                itemName TEXT NOT NULL, 
                itemAmount REAL NOT NULL
                )
            """.trimIndent())

                db.execSQL("""
                INSERT INTO expense_table_new (id, itemName, itemAmount)
                SELECT id, itemName, itemAmount FROM expense_table 
            """.trimIndent())

                db.execSQL("DROP TABLE expense_table")
                db.execSQL("ALTER TABLE expense_table_new RENAME TO expense_table")
            }

        }
    }
}