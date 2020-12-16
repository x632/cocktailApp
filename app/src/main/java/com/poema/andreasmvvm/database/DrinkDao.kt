@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.poema.andreasmvvm.db

import androidx.room.*

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