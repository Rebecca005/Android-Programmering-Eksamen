package com.example.pgrexam.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteItems" )
data class Favorite(
    @PrimaryKey
    val itemId: Int
)

