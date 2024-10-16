package com.example.pgrexam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.pgrexam.data.models.Favorite
import com.example.pgrexam.data.models.OrderHistory
import com.example.pgrexam.data.models.Shopping
import com.example.pgrexam.data.models.ShoppingBag
import com.example.pgrexam.data.room.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ShoppingRepository {
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _shoppingService = _retrofit.create(ShoppingService::class.java)

    private lateinit var _appDatabase: AppDatabase
    private val _shoppingDao by lazy { _appDatabase.shoppingDao() }
    private val _bagDao by lazy { _appDatabase.bagDao() }
    private val _orderHistoryDao by lazy { _appDatabase.orderHistoryDao() }
    private val _favoriteDao by lazy { _appDatabase.favoriteDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "shopping-database"
        ).fallbackToDestructiveMigration().build()
    }

    suspend fun getItems(): List<Shopping> {
        try {
            val response = _shoppingService.getAllItems()
            if (response.isSuccessful) {
                val item = response.body() ?: emptyList()
                _shoppingDao.insertItem(item)
                return _shoppingDao.getItem()
            } else {
                throw Exception("Response was not successful")
            }
        } catch (e: Exception) {
            Log.e("ShoppingRepository", "Failed to get list of items", e)
            return _shoppingDao.getItem()
        }
    }

    suspend fun getItemById(itemId: Int): Shopping? {
        return _shoppingDao.getItemById(itemId)
    }

    suspend fun getItemsByIds(idList: List<Int>): List<Shopping> {
        return _shoppingDao.getItemsByIds(idList)
    }

    // Add to bag
    suspend fun getBagItems(): List<ShoppingBag> {
        return _bagDao.getBagItems()
    }

    suspend fun addItem(item: ShoppingBag) {
        _bagDao.addItem(item)
    }

    suspend fun removeItem(shoppingBag: ShoppingBag) {
        _bagDao.removeItem(shoppingBag)
    }

    // Get order history
    suspend fun getOrderHistory(): List<OrderHistory> {
        return _orderHistoryDao.getOrderHistory()
    }

    suspend fun addOrder(item: OrderHistory) {
        _orderHistoryDao.addOrder(item)
    }

    // Make favorite
    suspend fun getFavorite(): List<Favorite> {
        return _favoriteDao.getFavorite()
    }

    suspend fun addFavorite(itemId: Favorite) {
        _favoriteDao.addFavorite(itemId)
    }

    suspend fun removeFavorite(item: Favorite) {
        _favoriteDao.removeFavorite(item)
    }
}
