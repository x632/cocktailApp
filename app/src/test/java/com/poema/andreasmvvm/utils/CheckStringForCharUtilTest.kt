package com.poema.andreasmvvm.utils

object CheckStringForCharUtilTest {

//veta ifall stringen inhåller char sequens

    private val existingDrinks = listOf("fanta", "vodka", "cola", "ale")


    fun checkStringForChar(
        drinkname: String
    ): Boolean {

        for (drink in existingDrinks){
            if (drink.contains(drinkname)){
                println("!!! $drink")

            }
            else {
                println("!!! alla drinkar inehåller inte char sequensen")
                return false
            }

        }
        return true
    }
}









//if (existingDrinks[0].contains(drinkname)){
//   return false
//}

/* if (drinkname.isEmpty()) {
     return false
 }

 if (drinkname in existingDrinks) {
     return true
 }


 if (drinkname !in existingDrinks) {
     return false
 }*/