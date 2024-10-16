package com.example.pgrexam.screens.shopping_list


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pgrexam.screens.common.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel,
    onItemClick: (itemId: Int) -> Unit,
    onCartClick: () -> Unit,
    onOrderHistoryClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val item = viewModel.item.collectAsState()
    val isLoading = viewModel.loading.collectAsState()
    val searchText = viewModel.searchText.collectAsState()

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            return
        }
    }
    Scaffold(topBar = {
        Column {
            TopAppBar(title = {
                Text(text = "Products")
            }, actions = {
                IconButton(onClick = { onFavoriteClick() }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Items"
                    )
                }
                IconButton(onClick = { onCartClick() }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart"
                    )
                }
                IconButton(onClick = { onOrderHistoryClick() }) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Order History"
                    )
                }
            })
            TextField(modifier = Modifier.fillMaxWidth(),
                value = searchText.value,
                onValueChange = viewModel::updateSearchText,
                placeholder = { Text("Search") })
        }
    }) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues, modifier = Modifier.fillMaxWidth()
        ) {
            items(item.value) { shopping ->
                ShoppingItem(shopping = shopping, onClick = {
                    onItemClick(shopping.id)
                })
            }
        }
    }
}