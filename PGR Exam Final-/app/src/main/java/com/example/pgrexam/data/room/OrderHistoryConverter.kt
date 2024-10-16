package com.example.pgrexam.data.room

import androidx.room.TypeConverter
import com.example.pgrexam.data.models.OrderHistory
import com.google.gson.Gson
import java.util.Date

object OrderHistoryConverter {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun orderHistoryFromString(value: String): OrderHistory {
        return gson.fromJson(value, OrderHistory::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun stringFromOrderHistory(orderHistory: OrderHistory): String {
        return gson.toJson(orderHistory)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    class DateConverter {
        @TypeConverter
        fun fromDate(date: Date?): Long? {
            return date?.time
        }

        @TypeConverter
        fun toDate(time: Long?): Date? {
            return time?.let { Date(it) }
        }
    }
}