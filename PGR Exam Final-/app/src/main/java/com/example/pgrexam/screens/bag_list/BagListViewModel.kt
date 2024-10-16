package com.example.pgrexam.screens.bag_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgrexam.data.ShoppingRepository
import com.example.pgrexam.data.models.OrderHistory
import com.example.pgrexam.data.models.Shopping
import com.example.pgrexam.data.models.ShoppingBag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class BagListViewModel : ViewModel() {


    private val _bagItems = MutableStateFlow<List<Shopping>>(emptyList())
    val bagItems = _bagItems.asStateFlow()

    private val _showOrderComplete = MutableStateFlow(false)
    val showOrderComplete = _showOrderComplete.asStateFlow()

    private val _itemId = MutableStateFlow<Int?>(null)
    val itemId: StateFlow<Int?> = _itemId

    fun loadItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfItemIds = ShoppingRepository.getBagItems().map { it.itemId }
            _bagItems.value = ShoppingRepository.getItemsByIds(listOfItemIds)

        }
    }

    fun placeOrder(items: List<Shopping>) {
        viewModelScope.launch(Dispatchers.IO) {
            ShoppingRepository.addOrder(
                OrderHistory(
                    items = items, date = Calendar.getInstance().time
                )
            )
            removeItems(items)
            _showOrderComplete.value = true
        }
    }

    fun setOrderCompleteShown() {
        _showOrderComplete.value = false
    }

    private fun removeItems(items: List<Shopping>) {
        viewModelScope.launch {
            items.forEach { item ->
                ShoppingRepository.removeItem(ShoppingBag(itemId = item.id))
            }
            loadItems()
        }
    }

    fun removeItem(item: Shopping) {
        viewModelScope.launch {
            ShoppingRepository.removeItem(ShoppingBag(itemId = item.id))
            loadItems()
        }
    }
}
