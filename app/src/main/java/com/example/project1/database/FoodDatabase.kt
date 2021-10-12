package com.example.project1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.project1.Food

@Database(entities = [ Food::class ], version=1)
@TypeConverters(FoodTypeConverters::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}
