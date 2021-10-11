package com.example.project1.database

import androidx.room.TypeConverter
import com.example.project1.Item
import java.nio.file.attribute.PosixFilePermissions.fromString
import java.util.*

class ItemTypeConverters {
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
    fun toItem(item: String): Item? {
        var total = item.split(":")
        return Item(UUID.fromString(total[0]),total[1], total[2].toInt())
    }
    @TypeConverter
    fun fromItem(item: Item): String? {
        return item.id.toString().plus(":").plus(item.name).plus(":").plus(item.date.toString())
    }
}