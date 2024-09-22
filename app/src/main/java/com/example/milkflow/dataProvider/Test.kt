package com.example.milkflow.dataProvider

import com.example.milkflow.model.PersonModel

fun main() {

    val list =DataProvider.list
    println(list)
    val p = PersonModel(1,"Ali", 110, 90)
    list.remove(p)
    println(list)
}