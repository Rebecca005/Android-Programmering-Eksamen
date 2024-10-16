package com.example.pgrexam.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pgrexam.data.room.ShoppingTypeConverter

@Entity
@TypeConverters(ShoppingTypeConverter::class)
data class Shopping(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,

)

data class Rating(
    var rate: Float,
    val count: Int
)
