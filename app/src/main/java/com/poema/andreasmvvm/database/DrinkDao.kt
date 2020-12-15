package com.poema.andreasmvvm.database

import androidx.room.*
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.TestDrink

@Dao// data access object
interface DrinkDao {
    @Query("SELECT * FROM Drink")
    fun getAllDrinks(): List<Drink>

    @Insert
    fun insert(drink: Drink):Long

    @Delete
    fun delete(drink: Drink)

    @Query("DELETE FROM Drink")
    fun deleteAll()



}