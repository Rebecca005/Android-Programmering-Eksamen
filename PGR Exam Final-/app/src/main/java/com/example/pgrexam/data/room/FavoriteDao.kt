package com.example.pgrexam.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgrexam.data.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM FavoriteItems")
    suspend fun getFavorite(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(item: Favorite)

    @Delete
    suspend fun removeFavorite(item: Favorite)
}