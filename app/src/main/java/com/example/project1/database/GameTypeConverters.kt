package com.example.project1.database

import androidx.room.TypeConverter
import com.example.project1.Game
import com.example.project1.Team
import java.nio.file.attribute.PosixFilePermissions.fromString
import java.util.*

class GameTypeConverters {
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
//Team("Team A", 0, false)
// “Team The Woz:42:false”
    //in Team class:
    //over ride toString
    //add fromString


    @TypeConverter
    fun toTeam(team: String): Team? {
        var total = team.split(":")
        return Team(UUID.fromString(total[0]),total[1], total[2].toInt(), total[3].toBoolean())
    }
    @TypeConverter
    fun fromTeam(team: Team): String? {
        return team.id.toString().plus(":").plus(team.name).plus(":").plus(team.score.toString()).plus(":").plus(team.isWinner.toString())
    }
}