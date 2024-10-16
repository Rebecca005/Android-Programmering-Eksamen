package com.example.pgrexam.screens.favorite_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pgrexam.screens.common.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    viewModel: FavoriteListViewModel,
    onBackButtonClick: () -> Unit,
    onFavoriteClick: (itemId: Int) -> Unit,
) {
    val items = viewModel.favoriteItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Your Favorite Items")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackButtonClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider()

                if (items.value.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(paddingValues),
                        text = "No favorites yet 💔 "
                    )
                } else {

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(items.value) { shopping ->
                            ShoppingItem(
                                shopping = shopping,
                                onClick = {
                                    onFavoriteClick(shopping.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}











