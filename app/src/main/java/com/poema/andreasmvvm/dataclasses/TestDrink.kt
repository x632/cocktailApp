package com.example.apicall.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestDrink(
    @ColumnInfo(name = "type_of_drink") val name: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "alcoholic") val working: Boolean
){
    @PrimaryKey(autoGenerate = true) var uid: Int? = null
}