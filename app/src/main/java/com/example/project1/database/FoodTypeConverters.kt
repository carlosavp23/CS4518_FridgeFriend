package com.example.project1.database

import androidx.room.TypeConverter
import com.example.project1.Food
import java.util.*

class FoodTypeConverters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toFood(item: String): Food? {
        var total = item.split(":")
        return Food(UUID.fromString(total[0]),total[1].toBoolean(), total[2], total[3], total[4])
    }
    @TypeConverter
    fun fromFood(food: Food): String? {
        return food.id.toString().plus(":")
            .plus(food.isChecked.toString()).plus(":")
            .plus(food.name).plus(":")
            .plus(food.imageString).plus(":")
            .plus(food.expiration)
    }
}