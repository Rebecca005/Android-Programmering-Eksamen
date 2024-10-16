package com.example.pgrexam.screens.shopping_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgrexam.data.ShoppingRepository
import com.example.pgrexam.data.models.Favorite
import com.example.pgrexam.data.models.Shopping
import com.example.pgrexam.data.models.ShoppingBag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ShoppingDetailsViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedItem = MutableStateFlow<Shopping?>(null)
    val selectedItem = _selectedItem.asStateFlow()

    private val _inBag = MutableStateFlow(false)
    val inBag = _inBag.asStateFlow()

    private val _favorited = MutableStateFlow(false)
    val favorited = _favorited.asStateFlow()

    fun setSelectedItem(itemId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedItem.value = ShoppingRepository.getItemById(itemId)
            _inBag.value = isCurrentItemInBag()
            _favorited.value = isCurrentItemFavorited()
            _loading.value = false
        }
    }

    fun updateBag(itemId: Int) {
        viewModelScope.launch {
            if (inBag.value) {
                ShoppingRepository.removeItem(ShoppingBag(itemId))
            } else {
                ShoppingRepository.addItem(ShoppingBag(itemId))
            }
            _inBag.value = isCurrentItemInBag()
        }
    }

    private suspend fun isCurrentItemInBag(): Boolean {
        return ShoppingRepository.getBagItems().any {
            it.itemId ==
                    selectedItem.value?.id
        }
    }

    fun updateFavorite(itemId: Int) {
        viewModelScope.launch {
            if (favorited.value) {
                ShoppingRepository.removeFavorite(Favorite(itemId))
            } else {
                ShoppingRepository.addFavorite(Favorite(itemId))
            }
            _favorited.value = isCurrentItemFavorited()
        }
    }

    private suspend fun isCurrentItemFavorited(): Boolean {
        return ShoppingRepository.getFavorite().any { it.itemId == selectedItem.value?.id }
    }
}