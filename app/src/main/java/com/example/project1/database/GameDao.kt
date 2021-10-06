package com.example.project1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.project1.Game
import java.util.*

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getGames(): LiveData<List<Game>>
    @Query("SELECT * FROM game WHERE id=(:id)")
    fun getGame(id: UUID): LiveData<Game?>
}

