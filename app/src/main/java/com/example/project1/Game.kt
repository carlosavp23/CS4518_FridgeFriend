package com.example.project1

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Game(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var teamA: Team = Team(UUID.randomUUID(),"Team A", 0, false),
                var teamB: Team = Team(UUID.randomUUID(),"Team B", 0, false),
                var date: Date = Date(),
                var title: String = ""
) {

    val photoFileNameTeamA
        get() = "IMG_${teamA.id}.jpg"

    val photoFileNameTeamB
        get() = "IMG_${teamB.id}.jpg"
}


