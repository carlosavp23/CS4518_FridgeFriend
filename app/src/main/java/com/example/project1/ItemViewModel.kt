package com.example.project1

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "ItemViewModel"

class ItemViewModel : ViewModel() {
    val items = mutableListOf<Item>()

    init {
        for (i in 0 until 100) {
            val item = Item()
            item.title = "Item #$i"
            games += game
        }
    }
    var currentIndex = 0
    var isGameOverCalled = false
    private val teamA = Team("Team A", 0, false)


    fun addPoints(team: String, numPoints: Int): String {
        Log.i(TAG, "addPoints in BBViewModel")
        if(team.equals("A")){
            teamA.score = teamA.score + numPoints
            return teamA.score.toString()
        } else{
            teamB.score = teamB.score + numPoints
            return teamB.score.toString()
        }
    }

    fun checkWinner(team1: String, team2: String): Boolean {
        Log.i(TAG, "checkWinner in BBViewModel")
        if(team1.equals("A")){
            return teamA.score >= teamB.score
        } else if (team2.equals("A")){
            return teamB.score >= teamA.score
        }
        return false
    }

    fun getScore(team: String): String {
        Log.i(TAG, "getScore in BBViewModel")
        if(team.equals("A")){
            return teamA.score.toString()
        } else if (team.equals("B")){
            return teamB.score.toString()
        }
        return "-1"
    }

    fun setScore(team: String, score: Int): Void? {
        Log.i(TAG, "setScore in BBViewModel")
        if(team.equals("A")){
            teamA.score = score
        } else if (team.equals("B")){
            teamB.score = score
        }
        return null
    }

    fun setIsWinner(team: String, isWinner: Boolean): Void? {
        Log.i(TAG, "setIsWinner in BBViewModel")
        if(team.equals("A")){
            teamA.isWinner = isWinner
        } else if (team.equals("B")){
            teamB.isWinner = isWinner
        }
        return null
    }

    fun getIsWinner(team: String): Boolean {
        Log.i(TAG, "getIsWinner in BBViewModel")
        if(team.equals("A")){
            return teamA.isWinner
        } else if (team.equals("B")){
            return teamB.isWinner
        }
        return false
    }

    fun setIsGameOverCalled(value: Boolean): Void? {
        isGameOverCalled = value
        return null
    }

    fun getIsGameOverCalled(): Boolean {
        return isGameOverCalled
    }

    fun setTeamAName(name: String): Void? {
        teamA.name = name
        return null
    }

    fun setTeamBName(name: String): Void? {
        teamB.name = name
        return null
    }

    fun getTeamAName(): String {
        return teamA.name
    }

    fun getTeamBName(): String {
        return teamB.name
    }
}