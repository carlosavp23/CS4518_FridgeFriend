package com.example.project1.com.example.project1

import com.example.project1.Team
import java.util.*

data class Game(var teamA: Team = Team("Team A", 0, false),
                var teamB: Team = Team("Team B", 0, false),
                var date: Date = Date(),
                var title: String = ""
) {

}