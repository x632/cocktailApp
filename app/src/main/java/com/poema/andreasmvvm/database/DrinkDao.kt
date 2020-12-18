
package com.poema.andreasmvvm.database

import androidx.room.*
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.TestDrink


@Dao// data access object
interface DrinkDao {
    @Query("SELECT * FROM Drink ORDER BY strDrink ASC")
    fun getAllDrinks(): List<Drink>

    @Insert
    fun insert(drink: Drink):Long

    @Delete
    fun delete(drink: Drink)

    @Query("DELETE FROM Drink")
    fun deleteAll()

    @Query("SELECT * FROM Drink WHERE idDrink = :drinkId LIMIT 1" )
    fun findDrinkById(drinkId: String) : Drink

}