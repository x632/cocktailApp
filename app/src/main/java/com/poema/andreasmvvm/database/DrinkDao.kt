@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.apicall.db

import androidx.room.*
import com.poema.andreasmvvm.dataclasses.Drink

@Dao// data acces object
interface DrinkDao {
    @Query("SELECT * FROM testdrink")
    fun getAllDrinks(): List<TestDrink>

    @Insert
    fun insert(vararg testDrink: TestDrink)

    @Delete
    fun delete(testDrink: TestDrink)

    @Query("DELETE FROM testdrink")
    fun deleteAll()



}