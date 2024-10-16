package com.example.pgrexam.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BagItems")
data class ShoppingBag(
    @PrimaryKey
    val itemId: Int

)
