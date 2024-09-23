package com.example.milkflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "person_table")
data class PersonModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var personName: String = "",
    var personRate: Int = 0,
    var personQuantity: Int = 0,
    val personType: String
)


@Entity(tableName = "expense_table")
data class ExpenseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var itemName : String,
    var itemAmount : Int,

)

