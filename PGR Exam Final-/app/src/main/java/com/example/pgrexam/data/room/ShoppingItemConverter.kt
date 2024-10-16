package com.example.pgrexam.data.room

import androidx.room.TypeConverter
import com.example.pgrexam.data.models.Rating
import com.example.pgrexam.data.models.Shopping
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ShoppingTypeConverter {

    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromStringList(value: String): List<Shopping> {
        val listType = object : TypeToken<List<Shopping>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromListString(list: List<Shopping>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun ratingFromString(value: String): Rating {
        return gson.fromJson(value, Rating::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun stringFromRating(rating: Rating): String {
        return gson.toJson(rating)
    }
}
