package com.example.pgrexam.data

import com.example.pgrexam.data.models.Shopping
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ShoppingService {
    @GET("products")
    suspend fun getAllItems(): Response<List<Shopping>>

    @GET("products/{id}")
    suspend fun getItem(@Path("id") id: Int): Response<Shopping>
}