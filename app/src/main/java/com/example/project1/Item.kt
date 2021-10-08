package com.example.project1

import androidx.room.PrimaryKey
import java.util.*

data class Item(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var name: String,
                var date: Int,
){
    val photoFileName
        get() = "IMG_$id.jpg"

}