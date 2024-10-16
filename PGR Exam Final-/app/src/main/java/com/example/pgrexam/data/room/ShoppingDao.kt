package com.example.pgrexam.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgrexam.data.models.Shopping

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM Shopping")
    suspend fun getItem(): List<Shopping>

    @Query("SELECT * FROM Shopping WHERE :itemId = id")
    suspend fun getItemById(itemId: Int): Shopping?

    @Query("SELECT * FROM Shopping WHERE id in (:idList)")
    suspend fun getItemsByIds(idList: List<Int>): List<Shopping>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(items: List<Shopping>)

}