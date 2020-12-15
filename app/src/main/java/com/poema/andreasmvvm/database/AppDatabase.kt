package com.example.apicall.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poema.andreasmvvm.dataclasses.Drinks

@Database(entities = [TestDrink::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}