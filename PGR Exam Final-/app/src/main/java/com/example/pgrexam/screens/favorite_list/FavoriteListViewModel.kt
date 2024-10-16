package com.example.pgrexam.screens.favorite_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgrexam.data.ShoppingRepository
import com.example.pgrexam.data.models.Shopping
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteListViewModel : ViewModel() {

    private val _favoriteItems = MutableStateFlow<List<Shopping>>(emptyList())
    val favoriteItems = _favoriteItems.asStateFlow()


    fun loadFavoriteItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfFavorites = ShoppingRepository.getFavorite().map { it.itemId }
            _favoriteItems.value = ShoppingRepository.getItemsByIds(listOfFavorites)
        }
    }


}