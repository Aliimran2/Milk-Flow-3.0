package com.example.milkflow.dataProvider

import com.example.milkflow.model.PersonModel

object DataProvider {
    var list : ArrayList<PersonModel> = arrayListOf()

    private fun addPerson(id:Int, name:String, rate: Int, quantity:Int){
        val p = PersonModel(id, name, rate, quantity)
        list.add(p)
    }

     fun remove(personModel: PersonModel){
        list.remove(personModel)
    }

    init {
        addPerson(1, "Ali", 110, 90)
        addPerson(2, "Naveed", 110, 90)
        addPerson(3, "akram", 110, 90)
        addPerson(4, "akbar", 110, 90)
        addPerson(5, "sultan", 110, 90)
        addPerson(6, "aslam", 110, 90)
        addPerson(7, "amjad", 110, 90)
    }
}