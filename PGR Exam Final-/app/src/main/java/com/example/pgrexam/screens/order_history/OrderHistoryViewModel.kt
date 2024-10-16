package com.example.pgrexam.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgrexam.data.ShoppingRepository
import com.example.pgrexam.data.models.OrderHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {
    private val _orderHistory = MutableStateFlow<List<OrderHistory>>(emptyList())
    val orderHistory = _orderHistory.asStateFlow()

    fun loadOrderHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            _orderHistory.value = ShoppingRepository.getOrderHistory().asReversed()
        }
    }
}