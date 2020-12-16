
package com.poema.andreasmvvm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poema.andreasmvvm.dataclasses.Drink
import com.poema.andreasmvvm.dataclasses.Drinks
import com.poema.andreasmvvm.dataclasses.TestDrink


@Database(entities = [Drink::class], version = 12)
abstract class AppDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}