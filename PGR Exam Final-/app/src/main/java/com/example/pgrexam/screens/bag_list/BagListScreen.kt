package com.example.pgrexam.screens.bag_list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pgrexam.screens.common.ShoppingItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BagListScreen(
    viewModel: BagListViewModel,
    onBackButtonClick: () -> Unit,
    onItemClick: (itemId: Int) -> Unit,
) {
    val items = viewModel.bagItems.collectAsState()
    val showOrderComplete = viewModel.showOrderComplete.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Your Shopping Bag")
            },
            navigationIcon = {
                IconButton(onClick = { onBackButtonClick() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back"
                    )
                }
            },
        )
    }, floatingActionButton = {
        Button(
            onClick = { viewModel.placeOrder(items.value) }, enabled = items.value.isNotEmpty()
        ) {
            Text(text = "Place Order ($ ${items.value.sumOf { it.price.toBigDecimal() }})")
        }
    }, floatingActionButtonPosition = FabPosition.Center, snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { paddingValues ->
        LaunchedEffect(showOrderComplete.value) {
            launch {
                if (showOrderComplete.value) {
                    snackbarHostState.showSnackbar(
                        message = "Order completed! 💸",
                        duration = SnackbarDuration.Short,
                    )
                    viewModel.setOrderCompleteShown()
                }
            }
        }
        if (items.value.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
                    .padding(paddingValues),
                text = "You have no items in cart 🤷‍"
            )
        } else {
            LazyColumn(
                contentPadding = paddingValues, modifier = Modifier.fillMaxWidth()
            ) {
                items(items.value) { shopping ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ShoppingItem(
                            modifier = Modifier.weight(1f),
                            shopping = shopping,
                            onClick = {
                                onItemClick(shopping.id)
                            })
                        IconButton(onClick = { viewModel.removeItem(shopping) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove Item"
                            )
                        }
                    }
                }
            }
        }
    }
}
