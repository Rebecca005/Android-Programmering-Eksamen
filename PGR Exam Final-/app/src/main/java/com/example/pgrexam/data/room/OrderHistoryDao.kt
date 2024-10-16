package com.example.pgrexam.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgrexam.data.models.OrderHistory

@Dao
interface OrderHistoryDao {
    @Query("SELECT * FROM OrderHistory")
    suspend fun getOrderHistory(): List<OrderHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(item: OrderHistory)

}