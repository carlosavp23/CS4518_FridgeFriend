package com.example.project1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.project1.Item
import java.util.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getItem(): LiveData<List<Item>>

    @Query("SELECT * FROM item WHERE id=(:id)")
    fun getItem(id: UUID): LiveData<Item?>

    @Update
    fun updateItem(game: Item)

    @Insert
    fun addItem(game: Item)
}