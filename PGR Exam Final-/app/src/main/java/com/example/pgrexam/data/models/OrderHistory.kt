package com.example.pgrexam.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pgrexam.data.room.OrderHistoryConverter
import com.example.pgrexam.data.room.ShoppingTypeConverter
import java.util.Date

@Entity
@TypeConverters(OrderHistoryConverter::class, ShoppingTypeConverter::class)
data class OrderHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val items: List<Shopping>,
    val date: Date
)
