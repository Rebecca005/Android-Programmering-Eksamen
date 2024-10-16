package com.example.pgrexam.screens.shopping_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgrexam.data.ShoppingRepository
import com.example.pgrexam.data.models.Shopping
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingListViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _item = MutableStateFlow<List<Shopping>>(emptyList())
    val item = _item.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _item.value = ShoppingRepository.getItems()
            _loading.value = false
        }
    }

    fun updateSearchText(searchText: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _searchText.value = searchText

            _item.value = ShoppingRepository.getItems().filter { shopping ->
                shopping.title.contains(searchText) || shopping.description.contains(searchText) || shopping.category.contains(
                    searchText
                )
            }
        }
    }
}

