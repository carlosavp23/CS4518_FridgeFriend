package com.example.project1

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.project1.com.example.project1.Food
import com.example.project1.database.FoodDatabase
import java.io.File
import java.util.*

private const val DATABASE_NAME = "food-database"

class FoodRepository private constructor(context: Context) {
    private val filesDir = context.applicationContext.filesDir
    private val database : FoodDatabase = Room.databaseBuilder(
        context.applicationContext,
        FoodDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val foodDao = database.foodDao()

    fun getFood(): LiveData<List<Food>> = foodDao.getFood()

    fun getFood(id: UUID): LiveData<Food?> = foodDao.getFood(id)

    fun getPhotoFile(food: Food): File = File(filesDir, food.photoFileName)


    companion object {
        private var INSTANCE: FoodRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FoodRepository(context)
            }
        }
        fun get(): FoodRepository {
            return INSTANCE ?:
            throw IllegalStateException("GameRepository must be initialized")
        }
    }
}