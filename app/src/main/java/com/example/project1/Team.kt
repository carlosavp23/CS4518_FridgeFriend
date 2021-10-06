package com.example.project1

import androidx.room.PrimaryKey
import java.util.*

data class Team(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var name: String,
                var score: Int,
                var isWinner: Boolean
                ) {
//put from string
}