
package com.poema.andreasmvvm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.poema.andreasmvvm.dataclasses.Drink


@Database(entities = [Drink::class], version = 34)
abstract class AppDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}