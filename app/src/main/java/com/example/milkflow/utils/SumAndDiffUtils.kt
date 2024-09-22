package com.example.milkflow.utils

import com.example.milkflow.model.PersonModel

object SumAndDiffUtils {

    fun updateTotal(persons: List<PersonModel>): Int {
        val total = persons.sumOf { it.personQuantity * it.personRate }
        return total
    }
}