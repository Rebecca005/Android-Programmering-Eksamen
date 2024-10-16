package com.example.pgrexam.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pgrexam.data.models.Favorite
import com.example.pgrexam.data.models.OrderHistory
import com.example.pgrexam.data.models.Shopping
import com.example.pgrexam.data.models.ShoppingBag

@Database(
    entities = [Shopping::class, ShoppingBag::class, OrderHistory::class, Favorite::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
    abstract fun bagDao(): BagDao
    abstract fun orderHistoryDao(): OrderHistoryDao
    abstract fun favoriteDao(): FavoriteDao

}