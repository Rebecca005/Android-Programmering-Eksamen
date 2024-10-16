package com.example.pgrexam.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgrexam.data.models.ShoppingBag

@Dao
interface BagDao {
    @Query("SELECT * FROM BagItems")
    suspend fun getBagItems(): List<ShoppingBag>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: ShoppingBag)

    @Delete
    suspend fun removeItem(item: ShoppingBag)










}