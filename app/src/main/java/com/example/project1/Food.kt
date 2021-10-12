package com.example.project1

import androidx.room.PrimaryKey
import java.util.*

data class Food(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var isChecked: Boolean = false,
                var name: String = "",
                var imageString: String = "",
                var expiration: String = ""
) {
    val photoFileName
        get() = "IMG_$id.jpg"

}