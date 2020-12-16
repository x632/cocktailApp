package com.poema.andreasmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TestDrink::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}